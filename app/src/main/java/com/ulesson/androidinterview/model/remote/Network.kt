package com.ulesson.androidinterview.model.remote

interface Network {
    suspend fun hasInternet(): Boolean
}