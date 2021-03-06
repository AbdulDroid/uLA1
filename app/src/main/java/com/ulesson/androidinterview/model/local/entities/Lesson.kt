package com.ulesson.androidinterview.model.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Lesson(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val icon: String = "",
    val media_url: String = "",
    val subject_id: Int = 0,
    val chapter_id: Int = 0
) : Parcelable

fun Lesson.toRecentlyViewed() =
    RecentlyViewed(
        id,
        name,
        icon,
        media_url,
        subject_id,
        chapter_id
    )