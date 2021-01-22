package com.ulesson.androidinterview.utils

import com.ulesson.androidinterview.model.local.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//helper data

const val chapterName = "NdY-01"
val lesson = Lesson()

//ChapterRepository helper methods

fun getChapterWithLessons(count: Int): Flow<List<ChapterWithLessons>> =
    flow {
        val list = arrayListOf<ChapterWithLessons>()
        val lessons = listOf(lesson)
        for (i in 0 until count) {
            list.add(
                ChapterWithLessons(
                    Chapter(id = i),
                    lessons = if (i % 2 == 0) lessons else emptyList()
                )
            )
        }
        emit(list)
    }

//LessonRepository helper methods

fun getLessons(count: Int) = flow {
    val list = arrayListOf<Lesson>()
    for (i in 0 until count) {
        list.add(Lesson(id = i))
    }
    emit(list.toList())
}

fun getRecentlyViewedWithSubject(count: Int) = flow {
    val list = arrayListOf<RecentlyViewedWithSubject>()
    for (i in 0 until count) {
        list.add(RecentlyViewedWithSubject(RecentlyViewed(i), Subject(id = i)))
    }
    emit(list.toList())
}