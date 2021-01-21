package com.ulesson.androidinterview.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ulesson.androidinterview.model.local.dao.SubjectDao
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.RecentlyViewed
import com.ulesson.androidinterview.model.local.entities.Subject

@Database(
    entities = [Chapter::class, Subject::class, Lesson::class, RecentlyViewed::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun lessonDao(): LessonDao
}