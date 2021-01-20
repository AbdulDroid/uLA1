package com.ulesson.androidinterview.model.models

import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject

data class SubjectsResponse(
    val data: ResponseData
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