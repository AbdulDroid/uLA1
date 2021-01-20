package com.ulesson.androidinterview.model.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    foreignKeys = [ForeignKey(
        entity = Subject::class,
        parentColumns = ["id"],
        childColumns = ["subject_id"],
        onDelete = CASCADE
    )]
)
@Parcelize
data class Chapter(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val subject_id: Int = 0
) : Parcelable