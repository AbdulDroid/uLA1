package com.ulesson.androidinterview.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject
import javax.inject.Inject
import javax.inject.Provider

class NavDispatcherImpl @Inject constructor(
    private val controller: Provider<NavController>
): NavDispatcher {
    override fun openSubject(subject: Subject) {
        controller.get().navigate(
            R.id.to_subjectFragment,
            bundleOf("subject" to subject)
        )
    }

    override fun openLesson(lesson: Lesson, name: String) {
        controller.get().navigate(
            R.id.to_lessonFragment,
            bundleOf("lesson" to lesson, "chapterName" to name)
        )
    }

    override fun openRecentlyPlayed(lesson: Lesson) {
        controller.get().navigate(
            R.id.to_lessonFragment,
            bundleOf("lesson" to lesson)
        )
    }

    override fun navigateUp() {
        controller.get().navigateUp()
    }
}