package com.ulesson.androidinterview.model.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Subject(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val icon: String = ""
): Parcelable