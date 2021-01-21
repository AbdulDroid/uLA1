package com.ulesson.androidinterview.model.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ChapterWithLessons (
    @Embedded
    val chapter: Chapter,
    @Relation(parentColumn = "id", entityColumn = "chapter_id")
    val lessons: List<Lesson>
)