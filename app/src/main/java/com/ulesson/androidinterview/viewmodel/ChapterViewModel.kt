package com.ulesson.androidinterview.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import com.ulesson.androidinterview.model.repositories.ChapterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChapterViewModel @ViewModelInject constructor(
    private val repo: ChapterRepository
): BaseViewModel() {

    private val _chapters = MediatorLiveData<List<ChapterWithLessons>>()
    val chapters: LiveData<List<ChapterWithLessons>> = _chapters

    private val _chapterName = MutableLiveData<String>()
    val chapterName: LiveData<String> = _chapterName

    fun getChapters(id: Int) = viewModelScope.launch {
        loadResult {
            _chapters.addSource(repo.getChapters(id).asLiveData(Dispatchers.IO)) {
                _chapters.value = it
            }
        }
    }

    fun getChapterNameById(id: Int) = viewModelScope.launch {
        loadResult {
            _chapterName.value = repo.getChapterNameById(id)
        }
    }
}