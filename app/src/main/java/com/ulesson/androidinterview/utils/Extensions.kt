package com.ulesson.androidinterview.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever


val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()