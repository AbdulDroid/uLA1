package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.LessonAndSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LessonRepository @Inject constructor(
    private val dao: LessonDao
) {

    suspend fun getLessons(subject_id: Int, chapter_id: Int): LiveData<List<Lesson>> =
        withContext(Dispatchers.IO) {
            dao.getLessons(chapter_id, subject_id)
        }

    suspend fun setAsRecentlyRead(lesson: Lesson) = withContext(Dispatchers.IO) {
        dao.updateLesson(lesson.copy(recently_played = true))
    }

    suspend fun getRecentlyPlayed(): LiveData<List<LessonAndSubject>> = withContext(Dispatchers.IO) {
        dao.getSubjectAndLessons()
    }
}