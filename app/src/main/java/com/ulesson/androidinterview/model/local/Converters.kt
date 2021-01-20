package com.ulesson.androidinterview.model.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import java.lang.reflect.Type

class Converters {

    private val gSon = Gson()

    private inline fun <reified T> getType(): Type {
        return TypeToken.getParameterized(List::class.java, T::class.java).type
    }

    @TypeConverter
    fun lessonsToString(lesson: List<Lesson>): String = gSon.toJson(lesson)

    @TypeConverter
    fun stringToLessons(value: String): List<Lesson> = gSon.fromJson(value, getType<Lesson>())

    @TypeConverter
    fun chaptersToString(chapter: List<Chapter>): String = gSon.toJson(chapter)

    @TypeConverter
    fun stringToChapters(value: String): List<Chapter> = gSon.fromJson(value, getType<Chapter>())
}