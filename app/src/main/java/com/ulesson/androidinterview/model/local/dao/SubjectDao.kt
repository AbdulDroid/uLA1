package com.ulesson.androidinterview.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ulesson.androidinterview.model.local.entities.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubjects(subjects: List<Subject>)

    @Query("select * from subject")
    fun getSubjects(): Flow<List<Subject>>
}