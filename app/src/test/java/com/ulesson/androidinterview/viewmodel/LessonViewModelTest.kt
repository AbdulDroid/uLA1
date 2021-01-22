package com.ulesson.androidinterview.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.RecentlyViewedWithSubject
import com.ulesson.androidinterview.model.repositories.LessonRepository
import com.ulesson.androidinterview.utils.getRecentlyViewedWithSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LessonViewModelTest {

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Mock
    lateinit var recentlyViewedObserver: Observer<List<RecentlyViewedWithSubject>>

    @Mock
    lateinit var dao: LessonDao

    @Mock
    lateinit var repository: LessonRepository

    private lateinit var viewModel: LessonViewModel

    @ObsoleteCoroutinesApi
    private val threadContext = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(threadContext)
        viewModel = LessonViewModel(repository)
    }

    @Test
    fun `viewModel successfully fetches recentlyPlayed from repository`(){
        runBlocking {
            val recentlyPlayed = getRecentlyViewedWithSubject(4)
            Mockito.`when`(repository.getRecentlyPlayed()).thenReturn(recentlyPlayed)

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.recentlyPlayed.observeForever(recentlyViewedObserver)

            viewModel.getRecentlyPlayed()

            Thread.sleep(100)

            verify(loadingObserver).onChanged(true)
            verify(recentlyViewedObserver).onChanged(recentlyPlayed.first())
            Mockito.verifyNoInteractions(errorObserver)
            verify(loadingObserver).onChanged(false)
        }
    }
}