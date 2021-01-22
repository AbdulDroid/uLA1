package com.ulesson.androidinterview.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import com.ulesson.androidinterview.model.repositories.ChapterRepository
import com.ulesson.androidinterview.utils.chapterName
import com.ulesson.androidinterview.utils.getChapterWithLessons
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
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ChapterViewModelTest {

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Mock
    lateinit var dataObserver: Observer<List<ChapterWithLessons>>

    @Mock
    lateinit var chapterNameObserver: Observer<String>

    @Mock
    lateinit var repository: ChapterRepository

    private lateinit var viewModel: ChapterViewModel

    @ObsoleteCoroutinesApi
    private val threadContext = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(threadContext)
        viewModel = ChapterViewModel(repository)
    }

    @Test
    fun `viewModel successfully fetches chapters with lessons from repository`(){
        runBlocking {
            val chaptersWithLessons = getChapterWithLessons(5)
            Mockito.`when`(repository.getChapters(0)).thenReturn(chaptersWithLessons)

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.chapters.observeForever(dataObserver)

            viewModel.getChapters(0)

            Thread.sleep(100)

            Mockito.verify(loadingObserver).onChanged(true)
            Mockito.verify(dataObserver).onChanged(chaptersWithLessons.first())
            Mockito.verifyNoInteractions(errorObserver)
            Mockito.verify(loadingObserver).onChanged(false)
        }
    }

    @Test
    fun `viewModel successfully fetches chapter name from repository when the right chapter id is passed`(){
        runBlocking {

            Mockito.`when`(repository.getChapterNameById(0)).thenReturn(chapterName)

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.chapterName.observeForever(chapterNameObserver)

            viewModel.getChapterNameById(0)

            Thread.sleep(100)

            Mockito.verify(loadingObserver).onChanged(true)
            Mockito.verify(chapterNameObserver).onChanged(chapterName)
            Mockito.verifyNoInteractions(errorObserver)
            Mockito.verify(loadingObserver).onChanged(false)
        }
    }
}