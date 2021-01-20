package com.ulesson.androidinterview.model.models

import androidx.lifecycle.LiveData

data class UIData<T>(
    val errorMessage: String? = null,
    val data: LiveData<T?>? = null
)