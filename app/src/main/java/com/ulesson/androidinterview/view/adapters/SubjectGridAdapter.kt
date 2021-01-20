package com.ulesson.androidinterview.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Subject
import kotlinx.android.synthetic.main.subject_grid_item.view.*

class SubjectGridAdapter(private val listener: OnItemClickListener) : ListAdapter<Subject, SubjectGridAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.subject_grid_item, parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Subject) = with(itemView) {
            container.setBackgroundResource(
                when (item.name.toLowerCase()) {
                    "biology" -> R.drawable.biology_background
                    "mathematics" -> R.drawable.maths_background
                    "physics" -> R.drawable.physics_background
                    "english" -> R.drawable.english_background
                    "chemistry" -> R.drawable.chemistry_background
                    else -> R.drawable.maths_background
                }
            )
            Picasso.get().load(item.icon).into(icon)
            name.text = item.name
            setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Subject>() {

        override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem.id == oldItem.id && oldItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
            return oldItem == newItem
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Subject)
    }
}