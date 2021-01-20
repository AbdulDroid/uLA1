package com.ulesson.androidinterview.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.viewmodel.SubjectViewModel
import kotlinx.android.synthetic.main.lesson_list_item.view.*

class LessonListAdapter(
    private val listener: OnItemClickListener?
) : ListAdapter<Lesson, LessonListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.lesson_list_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Lesson) = with(itemView) {
            lessonName.text = item.name
            Log.e("LessonList", "icon ==> ${item.icon}")
            Picasso.get().load(item.icon).into(lessonImage)
            setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Lesson>() {

        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == oldItem.id && oldItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Lesson)
    }
}