package com.ulesson.androidinterview.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ulesson.androidinterview.model.local.entities.Subject
import com.ulesson.androidinterview.model.repositories.SubjectRepository
import com.ulesson.androidinterview.utils.getSubjectsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SubjectViewModelTest {

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var errorObserver: Observer<String>

    @Mock
    lateinit var dataObserver: Observer<List<Subject>>

    @Mock
    lateinit var repository: SubjectRepository

    private lateinit var viewModel: SubjectViewModel
    @ObsoleteCoroutinesApi
    private val threadContext = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(threadContext)
        viewModel = SubjectViewModel(repository)
    }

    @Test
    fun `viewModel successfully fetches subjects from repository`(){
        runBlocking {
            val subjects = getSubjectsViewModel(5)
            `when`(repository.getData()).thenReturn(subjects)

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.subjects.observeForever(dataObserver)

            viewModel.getSubjects()

            Thread.sleep(100)

            verify(loadingObserver).onChanged(true)
            verify(dataObserver).onChanged(subjects.first())
            verifyNoInteractions(errorObserver)
            verify(loadingObserver).onChanged(false)
        }
    }

    @Test
    fun `viewModel successfully returns empty list from the repository`(){
        runBlocking {
            val subjects = getSubjectsViewModel(0)
            `when`(repository.getData()).thenReturn(subjects)

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.subjects.observeForever(dataObserver)

            viewModel.getSubjects()

            Thread.sleep(100)

            verify(loadingObserver).onChanged(true)
            verify(dataObserver).onChanged(subjects.first())
            verifyNoInteractions(errorObserver)
            verify(loadingObserver).onChanged(false)
        }
    }


    @Test
    fun `viewModel error block is called when exception is thrown from the repository`(){
        runBlocking {
            `when`(repository.getData()).thenThrow(RuntimeException("An error occurred"))

            viewModel.loading.observeForever(loadingObserver)
            viewModel.error.observeForever(errorObserver)
            viewModel.subjects.observeForever(dataObserver)

            viewModel.getSubjects()

            Thread.sleep(100)

            verify(loadingObserver).onChanged(true)
            verify(errorObserver).onChanged("An error occurred")
            verifyNoInteractions(dataObserver)
            verify(loadingObserver).onChanged(false)
        }
    }
}