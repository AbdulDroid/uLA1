package com.ulesson.androidinterview.navigation

import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject

interface NavDispatcher {
    fun openSubject(subject: Subject)
    fun openLesson(lesson: Lesson, name: String)
    fun openRecentlyPlayed(lesson: Lesson)
    fun navigateUp()
}