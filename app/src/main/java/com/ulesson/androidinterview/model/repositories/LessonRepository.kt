package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.RecentlyViewed
import com.ulesson.androidinterview.model.local.entities.RecentlyViewedWithSubject
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
        dao.saveRecentlyViewed(
            RecentlyViewed(
                lesson.id,
                lesson.name,
                lesson.icon,
                lesson.media_url,
                lesson.subject_id,
                lesson.chapter_id
            )
        )
    }

    suspend fun getRecentlyPlayed(): LiveData<List<RecentlyViewedWithSubject>> =
        withContext(Dispatchers.IO) {
            dao.getSubjectAndRecentlyViewed()
        }
}