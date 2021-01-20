package com.ulesson.androidinterview.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.LessonAndSubject
import kotlinx.android.synthetic.main.recently_viewed_list_item.view.*

class RecentlyViewedListAdapter(private val listener: OnItemInteractionListener) :
    ListAdapter<LessonAndSubject, RecentlyViewedListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recently_viewed_list_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LessonAndSubject) = with(itemView) {
            lessonName.text = item.lesson.name
            subjectName.text = item.subject.name
            when (item.subject.name.toLowerCase()) {
                "biology" -> {
                    subjectName.setTextColor(ContextCompat.getColor(context, R.color.colorBio))
                    playBtn.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorBio),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                "physics" -> {
                    subjectName.setTextColor(ContextCompat.getColor(context, R.color.colorPhysics))
                    playBtn.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorPhysics),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                "english" -> {
                    subjectName.setTextColor(ContextCompat.getColor(context, R.color.colorEnglish))
                    playBtn.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorEnglish),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                "chemistry" -> {
                    subjectName.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorChemistry
                        )
                    )
                    playBtn.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorChemistry),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                else -> {
                    subjectName.setTextColor(ContextCompat.getColor(context, R.color.colorMaths))
                    playBtn.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorMaths),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }
            playBtn.setBackgroundResource(
                if (absoluteAdapterPosition % 2 != 0) R.drawable.play_bacground_right
                else R.drawable.play_background_left
            )

            Picasso.get().load(item.lesson.icon).into(thumbnail)

            setOnClickListener {
                listener.onItemClick(item.lesson)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LessonAndSubject>() {

        override fun areItemsTheSame(
            oldItem: LessonAndSubject,
            newItem: LessonAndSubject
        ): Boolean {
            return oldItem.lesson.id == newItem.lesson.id && oldItem.subject.id == newItem.subject.id
        }

        override fun areContentsTheSame(
            oldItem: LessonAndSubject,
            newItem: LessonAndSubject
        ): Boolean {
            return oldItem.lesson == newItem.lesson && oldItem.subject == newItem.subject
        }

    }

    interface OnItemInteractionListener {
        fun onItemClick(lesson: Lesson)
    }
}