package com.ulesson.androidinterview.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ulesson.androidinterview.utils.dp

class SubjectItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val pos = parent.getChildAdapterPosition(view)
        val leading = if (pos == 0) 28.dp else 14.dp
        outRect.run {
            top = leading
            bottom = 14.dp
        }
    }
}