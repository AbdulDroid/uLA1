package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChapterRepository @Inject constructor(
    private val dao: ChapterDao
) {

    suspend fun getChapters(id: Int): Flow<List<ChapterWithLessons>> =
        withContext(Dispatchers.IO) {
            dao.getChapterWithLessons(id)
        }

    suspend fun getChapterNameById(id: Int): String = withContext(Dispatchers.IO) {
        dao.getChapterNameById(id)
    }

}