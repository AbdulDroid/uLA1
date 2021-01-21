package com.ulesson.androidinterview.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.ChapterWithLessons
import com.ulesson.androidinterview.utils.SnapHelper
import com.ulesson.androidinterview.utils.decorators.LessonItemDecorator
import kotlinx.android.synthetic.main.chapter_list_item.view.*

class ChaptersListAdapter :
    ListAdapter<ChapterWithLessons, ChaptersListAdapter.ViewHolder>(DiffCallback()) {

    private var listener: LessonListAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chapter_list_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun setLessonListener(listener: LessonListAdapter.OnItemClickListener) {
        this.listener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ChapterWithLessons) = with(itemView) {
            val listAdapter = LessonListAdapter(listener)
            lessonList.apply {
                adapter = listAdapter
                addItemDecoration(LessonItemDecorator())
                SnapHelper().attachToRecyclerView(this)
            }
            listAdapter.submitList(item.lessons)
            chapterTitle.text = item.chapter.name
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChapterWithLessons>() {

        override fun areItemsTheSame(
            oldItem: ChapterWithLessons,
            newItem: ChapterWithLessons
        ): Boolean {
            return oldItem.chapter.id == newItem.chapter.id && oldItem.chapter.name == newItem.chapter.name
        }

        override fun areContentsTheSame(
            oldItem: ChapterWithLessons,
            newItem: ChapterWithLessons
        ): Boolean {
            return oldItem == newItem
        }

    }
}