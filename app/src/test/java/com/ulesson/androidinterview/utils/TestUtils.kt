package com.ulesson.androidinterview.utils

import com.ulesson.androidinterview.model.local.entities.Subject
import com.ulesson.androidinterview.model.models.ChapterData
import com.ulesson.androidinterview.model.models.ResponseData
import com.ulesson.androidinterview.model.models.SubjectData
import kotlinx.coroutines.flow.flow

val chapter = ChapterData()

fun getSubjects(count: Int): ResponseData {
    val list = arrayListOf<SubjectData>()
    for (i in 0 until count) {
        val subject = SubjectData(0, chapters = if (i % 2 == 0) listOf(chapter) else emptyList())
        list.add(subject.copy(id = i))
    }
    return ResponseData(subjects = list, status = "success", message = "success")
}

val subject = Subject()

fun getSubjectsViewModel(count: Int) = flow {
    val list = arrayListOf<Subject>()
    for (i in 0 until count) {
        list.add(subject.copy(i))
    }
    emit(list.toList())
}