package com.ulesson.androidinterview.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentlyViewed (
    val id: Int = 0,
    val name: String = "",
    val icon: String = "",
    val media_url: String = "",
    @PrimaryKey
    val subject_id: Int = 0,
    val chapter_id: Int = 0
)