package com.ulesson.androidinterview.model.remote

import com.ulesson.androidinterview.model.models.SubjectsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("content/grade")
    suspend fun getSubjects(): Response<SubjectsResponse>
}