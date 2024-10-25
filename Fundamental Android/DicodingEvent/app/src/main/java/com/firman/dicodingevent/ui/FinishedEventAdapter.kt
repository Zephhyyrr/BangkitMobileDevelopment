package com.firman.dicodingevent.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.databinding.ItemFinishedEventBinding
import com.firman.dicodingevent.ui.ui.detail.DetailActivity

class FinishedEventAdapter(private val onItemClick: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, FinishedEventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemFinishedEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class EventViewHolder(private val binding: ItemFinishedEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity, onItemClick: (EventEntity) -> Unit) {
            val name = if (event.name.length > 20) {
                event.name.substring(0, 20) + "..."
            } else {
                event.name
            }

            binding.tvNamaEvent.text = name

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.ivActivePicture)

            binding.btnSelengkapnya.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<EventEntity>() {
        override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
            return oldItem == newItem
        }
    }
}
