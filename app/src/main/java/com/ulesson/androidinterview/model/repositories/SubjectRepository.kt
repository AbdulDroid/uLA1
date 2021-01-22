package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.dao.LessonDao
import com.ulesson.androidinterview.model.local.dao.SubjectDao
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.Subject
import com.ulesson.androidinterview.model.remote.ApiService
import com.ulesson.androidinterview.model.remote.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(
    private val api: ApiService,
    private val dao: SubjectDao,
    private val lessonDao: LessonDao,
    private val chapterDao: ChapterDao,
    private val networkUtil: Network
) {

    suspend fun getData(): Flow<List<Subject>> =
        withContext(Dispatchers.IO) {
            val initial = dao.getSubjects()
            if (networkUtil.hasInternet()) {
                val resp = api.getSubjects()
                if (resp.isSuccessful) {
                    dao.saveSubjects(resp.body()?.data?.subjects!!.map {
                        Subject(it.id, it.name, it.icon)
                    })
                    resp.body()?.data?.subjects?.forEach {
                        chapterDao.saveChapter(it.chapters.map { c ->
                            Chapter(c.id, c.name, it.id)
                        })
                        it.chapters.forEach { c ->
                            lessonDao.saveLessons(c.lessons)
                        }
                    }
                } else {
                    throw Exception(resp.errorBody()?.string() ?: "An error occurred")
                }
            }
            initial
        }
}