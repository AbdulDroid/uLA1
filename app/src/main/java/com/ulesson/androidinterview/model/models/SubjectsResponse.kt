package com.ulesson.androidinterview.model.models

import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject

data class SubjectsResponse(
    val data: ResponseData = ResponseData()
)

data class ResponseData(
    val status: String = "",
    val message: String = "",
    val subjects: List<SubjectData> = emptyList()
)

data class SubjectData(
    val id: Int = 0,
    val name: String = "",
    val icon: String = "",
    val chapters: List<ChapterData> = emptyList()
)

data class ChapterData(
    val id: Int = 0,
    val name: String = "",
    val lessons: List<Lesson> = emptyList()
)

fun List<SubjectData>.toSubjects() = this.map { Subject(it.id, it.name, it.icon) }

fun List<ChapterData>.toChapters(subject_id: Int) = this.map { Chapter(it.id, it.name, subject_id) }