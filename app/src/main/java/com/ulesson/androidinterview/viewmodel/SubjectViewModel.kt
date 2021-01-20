package com.ulesson.androidinterview.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject
import com.ulesson.androidinterview.model.repositories.SubjectRepository
import kotlinx.coroutines.launch

class SubjectViewModel @ViewModelInject constructor(
    private val repo: SubjectRepository
) : BaseViewModel() {

    private val _subjects = MediatorLiveData<List<Subject>>()
    val subjects: LiveData<List<Subject>> = _subjects

    fun getSubjects() = viewModelScope.launch {
        loadResult {
            _subjects.addSource(repo.getData()) {
                _subjects.value = it
            }
        }
    }
}