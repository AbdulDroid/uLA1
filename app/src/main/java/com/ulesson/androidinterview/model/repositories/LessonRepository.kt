package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.RecentlyViewed
import com.ulesson.androidinterview.model.local.entities.RecentlyViewedWithSubject
import com.ulesson.androidinterview.model.local.entities.toRecentlyViewed
import com.ulesson.androidinterview.testing.OpenForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
@OpenForTesting
class LessonRepository @Inject constructor(
    private val dao: LessonDao
) {

    suspend fun getLessons(subject_id: Int, chapter_id: Int): Flow<List<Lesson>> =
        withContext(Dispatchers.IO) {
            dao.getLessons(chapter_id, subject_id)
        }

    suspend fun setAsRecentlyRead(lesson: Lesson) = withContext(Dispatchers.IO) {
        dao.saveRecentlyViewed(
            lesson.toRecentlyViewed()
        )
    }

    suspend fun getRecentlyPlayed(): Flow<List<RecentlyViewedWithSubject>> =
        withContext(Dispatchers.IO) {
            dao.getSubjectAndRecentlyViewed()
        }
}