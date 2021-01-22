package com.ulesson.androidinterview.model.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ulesson.androidinterview.model.local.AppDatabase
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.toRecentlyViewed
import com.ulesson.androidinterview.utils.getLessons
import com.ulesson.androidinterview.utils.getRecentlyViewedWithSubject
import com.ulesson.androidinterview.utils.lesson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class LessonRepositoryTest {
    private lateinit var lessonRepository: LessonRepository
    private val lessonDao = Mockito.mock(LessonDao::class.java)

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        `when`(db.lessonDao()).thenReturn(lessonDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        lessonRepository = LessonRepository(lessonDao)
    }

    @Test
    fun `test that getLessons returns data`() {
        runBlocking {
            `when`(lessonDao.getLessons(0, 0)).thenReturn(getLessons(3))
            val result = lessonRepository.getLessons(0, 0).first()
            assertThat(result.size, equalTo(3))
            assertThat(result[1].id, equalTo(1))
        }
    }

    @Test
    fun `test that getLessons returns emptyList when there is no data`() {
        runBlocking {
            `when`(lessonDao.getLessons(0, 0)).thenReturn(getLessons(0))
            val result = lessonRepository.getLessons(0, 0).first()
            assertThat(result.isEmpty(), equalTo(true))
        }
    }


    @Test
    fun `test that setAsRecentlyRead calls the right methods`() {
        runBlocking {
            lessonRepository.setAsRecentlyRead(lesson)
            verify(lessonDao, atLeastOnce()).saveRecentlyViewed(lesson.toRecentlyViewed())
            verifyNoMoreInteractions(lessonDao)
        }
    }

    @Test
    fun `test that getRecentlyPlayed returns data`() {
        runBlocking {
            `when`(lessonDao.getSubjectAndRecentlyViewed()).thenReturn(
                getRecentlyViewedWithSubject(
                    4
                )
            )
            val result = lessonRepository.getRecentlyPlayed().first()
            assertThat(result.size, equalTo(4))
            assertThat(result[3].lesson.id, equalTo(3))
            assertThat(result[2].subject.id, equalTo(2))
        }
    }
}