package com.ulesson.androidinterview.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import com.ulesson.androidinterview.model.repositories.ChapterRepository
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
            _chapters.addSource(repo.getChapters(id)) {
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