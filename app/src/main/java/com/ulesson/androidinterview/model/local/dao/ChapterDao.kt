package com.ulesson.androidinterview.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChapter(chapters: List<Chapter>)

    @Transaction
    @Query("select * from chapter where subject_id=:subject_id")
    fun getChapterWithLessons(subject_id: Int): Flow<List<ChapterWithLessons>>

    @Query("select name from chapter where id=:id limit 1")
    fun getChapterNameById(id: Int): String
}