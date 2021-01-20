package com.ulesson.androidinterview.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubjects(subjects: List<Subject>)

    @Query("select * from subject")
    fun getSubjects(): LiveData<List<Subject>>
}