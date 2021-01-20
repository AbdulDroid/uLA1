package com.ulesson.androidinterview.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.LessonAndSubject
import com.ulesson.androidinterview.model.repositories.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel @ViewModelInject constructor(
    private val repo: LessonRepository
): BaseViewModel() {

    private val _lessons = MediatorLiveData<List<Lesson>>()
    val lessons: LiveData<List<Lesson>> = _lessons

    private val _recentlyPlayed = MediatorLiveData<List<LessonAndSubject>>()
    val recentlyPlayed: LiveData<List<LessonAndSubject>> = _recentlyPlayed

    fun getLessons(subject_id: Int, chapter_id: Int) = viewModelScope.launch {
        loadResult {
            _lessons.addSource(repo.getLessons(subject_id, chapter_id)) {
                _lessons.value = it
            }
        }
    }

    fun getRecentlyPlayed() = viewModelScope.launch {
        loadResult {
            _recentlyPlayed.addSource(repo.getRecentlyPlayed()) {
                _recentlyPlayed.value = it
            }
        }
    }

    fun setAsRecentlyPlayed(lesson: Lesson) = viewModelScope.launch {
        loadResult {
            repo.setAsRecentlyRead(lesson)
        }
    }
}