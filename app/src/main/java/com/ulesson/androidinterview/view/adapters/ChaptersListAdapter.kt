package com.ulesson.androidinterview.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Chapter
import com.ulesson.androidinterview.utils.decorators.LessonItemDecorator
import com.ulesson.androidinterview.viewmodel.LessonViewModel
import kotlinx.android.synthetic.main.chapter_list_item.view.*

class ChaptersListAdapter(private val ctx: Fragment) :
    ListAdapter<Chapter, ChaptersListAdapter.ViewHolder>(DiffCallback()) {

    private var listener: LessonListAdapter.OnItemClickListener? = null
    private lateinit var listAdapter: LessonListAdapter
    private val model: LessonViewModel by ctx.viewModels()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chapter_list_item, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun setLessonListener(listener: LessonListAdapter.OnItemClickListener) {
        this.listener = listener
        listAdapter = LessonListAdapter(this.listener)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Chapter) = with(itemView) {
            lessonList.apply {
                adapter = listAdapter
                addItemDecoration(LessonItemDecorator())
            }
            model.lessons.observe(ctx.viewLifecycleOwner) {
                Log.e("ChapterAdapter", "Data == $it")
                listAdapter.submitList(it)
            }
            model.getLessons(item.subject_id, item.id)
            chapterTitle.text = item.name
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Chapter>() {

        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == oldItem.id && oldItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }

    }
}