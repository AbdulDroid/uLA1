package com.ulesson.androidinterview.model.repositories

import androidx.lifecycle.LiveData
import com.ulesson.androidinterview.model.local.dao.ChapterDao
import com.ulesson.androidinterview.model.local.entities.Chapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChapterRepository @Inject constructor(
    private val dao: ChapterDao
) {

    suspend fun getChapters(id: Int): LiveData<List<Chapter>> =
        withContext(Dispatchers.IO) {
            dao.getChapters(id)
        }

    suspend fun getChapterNameById(id: Int): String = withContext(Dispatchers.IO) {
        dao.getChapterNameById(id)
    }

}