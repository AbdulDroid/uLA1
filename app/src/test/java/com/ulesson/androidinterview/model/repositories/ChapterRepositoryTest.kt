package com.ulesson.androidinterview.model.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ulesson.androidinterview.model.local.AppDatabase
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.utils.*
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
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class ChapterRepositoryTest {
    private lateinit var chapterRepository: ChapterRepository
    private val chapterDao = Mockito.mock(ChapterDao::class.java)

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.chapterDao()).thenReturn(chapterDao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        chapterRepository = ChapterRepository(chapterDao)
    }

    @Test
    fun `test that getChapters returns empty list when there is no data`() {
        runBlocking {
            `when`(chapterDao.getChapterWithLessons(0)).thenReturn(getChapterWithLessons(0))
            val result = chapterRepository.getChapters(0).first()
            assertThat(result.isEmpty(), equalTo(true))
        }
    }

    @Test
    fun `test that getChapters returns data`() {
        runBlocking {
            `when`(chapterDao.getChapterWithLessons(0)).thenReturn(getChapterWithLessons(2))
            val result = chapterRepository.getChapters(0).first()
            assertThat(result.size, equalTo(2))
            assertThat(result[0].lessons.size, equalTo(1))
            assertThat(result[1].lessons.size, equalTo(0))
        }
    }

    @Test
    fun `test that getChapterNameById returns data`() {
        runBlocking {
            `when`(chapterDao.getChapterNameById(0)).thenReturn(chapterName)
            val result = chapterRepository.getChapterNameById(0)
            assertThat(result, equalTo(chapterName))
        }
    }
}