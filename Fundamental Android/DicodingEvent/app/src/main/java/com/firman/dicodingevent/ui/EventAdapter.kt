package com.firman.dicodingevent.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.databinding.ItemUpcomingEventBinding
import com.firman.dicodingevent.ui.ui.detail.DetailActivity

class EventAdapter(private val onItemClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            ItemUpcomingEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        Log.d("EventAdapter", "Event ID: ${event.id}")
        holder.bind(event, onItemClick)
    }

    class EventViewHolder(private val binding: ItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onItemClick: (ListEventsItem) -> Unit) {
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.ivActivePicture)
            binding.tvNamaEvent.text = event.name
            binding.btnSelengkapnya.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id.toString())
                binding.root.context.startActivity(intent)
            }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<ListEventsItem>() {
        override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
            return oldItem == newItem
        }
    }
}
