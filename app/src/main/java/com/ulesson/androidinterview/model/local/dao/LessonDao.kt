package com.ulesson.androidinterview.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.RecentlyViewed
import com.ulesson.androidinterview.model.local.entities.RecentlyViewedWithSubject

@Dao
interface LessonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLessons(lessons: List<Lesson>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecentlyViewed(recentlyViewed: RecentlyViewed)

    @Query("select * from lesson where chapter_id=:chapter_id and subject_id=:subject_id")
    fun getLessons(chapter_id: Int, subject_id: Int): LiveData<List<Lesson>>

    @Transaction
    @Query("select * from recentlyViewed")
    fun getSubjectAndRecentlyViewed(): LiveData<List<RecentlyViewedWithSubject>>
}