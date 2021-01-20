package com.ulesson.androidinterview.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ulesson.androidinterview.model.local.entities.Chapter

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChapter(chapters: List<Chapter>)

    @Query("select * from chapter where subject_id=:subject_id")
    fun getChapters(subject_id: Int): LiveData<List<Chapter>>

    @Query("select name from chapter where id=:id limit 1")
    fun getChapterNameById(id: Int): String
}