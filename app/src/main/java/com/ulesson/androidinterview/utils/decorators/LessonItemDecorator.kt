package com.ulesson.androidinterview.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ulesson.androidinterview.utils.dp

class LessonItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount ?: 0
        val leading = if (pos == 0) 27.dp else 14.dp
        val trailing = if (pos == count - 1) 27.dp else 0
        outRect.run {
            left = leading
            right = trailing
            top = 12.dp
            bottom = 4.dp
        }
    }
}