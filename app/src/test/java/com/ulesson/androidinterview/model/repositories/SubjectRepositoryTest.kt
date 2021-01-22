package com.ulesson.androidinterview.model.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ulesson.androidinterview.model.local.AppDatabase
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.dao.SubjectDao
import com.ulesson.androidinterview.model.models.SubjectsResponse
import com.ulesson.androidinterview.model.models.toChapters
import com.ulesson.androidinterview.model.models.toSubjects
import com.ulesson.androidinterview.model.remote.ApiService
import com.ulesson.androidinterview.model.remote.Network
import com.ulesson.androidinterview.utils.getSubjects
import com.ulesson.androidinterview.utils.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response

@RunWith(JUnit4::class)
class SubjectRepositoryTest {

    private lateinit var subjectRepository: SubjectRepository
    private val subjectDao = mock(SubjectDao::class.java)
    private val chapterDao = mock(ChapterDao::class.java)
    private val lessonDao = mock(LessonDao::class.java)
    private val apiService = mock(ApiService::class.java)
    private val network = mock(Network::class.java)

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.subjectDao()).thenReturn(subjectDao)
        `when`(db.chapterDao()).thenReturn(chapterDao)
        `when`(db.lessonDao()).thenReturn(lessonDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        subjectRepository =
            SubjectRepository(apiService, subjectDao, lessonDao, chapterDao, network)
    }

    @Test
    fun `remote call returns successfully with no data`() {
        kotlin.runCatching {
            runBlocking {
                `when`(apiService.getSubjects()).thenReturn(Response.success(SubjectsResponse()))
                `when`(network.hasInternet()).thenReturn(true)
                subjectRepository.getData()
                verify(apiService, atLeastOnce()).getSubjects()
                verify(subjectDao, never()).getSubjects()
                verify(subjectDao, never()).saveSubjects(emptyList())
                verify(chapterDao, never()).saveChapter(emptyList())
                verify(lessonDao, never()).saveLessons(emptyList())
                verifyNoMoreInteractions(subjectDao)
                verifyNoInteractions(chapterDao)
                verifyNoInteractions(lessonDao)
            }
        }
    }

    @Test
    fun `remote call returns successfully with data`() {
        runBlocking {
            val data = getSubjects(3)
            `when`(apiService.getSubjects()).thenReturn(
                Response.success(SubjectsResponse(data))
            )
            `when`(network.hasInternet()).thenReturn(true)
             subjectRepository.getData()

            verify(subjectDao, atLeastOnce()).getSubjects()
            verify(apiService, atLeastOnce()).getSubjects()
            verify(subjectDao, atLeastOnce()).saveSubjects(data.subjects.toSubjects())
            verify(chapterDao, atLeastOnce()).saveChapter(data.subjects[0].chapters.toChapters(0))
            verify(lessonDao, atLeastOnce()).saveLessons(emptyList())
            verifyNoMoreInteractions(subjectDao)
            verifyNoMoreInteractions(lessonDao)
        }
    }
}