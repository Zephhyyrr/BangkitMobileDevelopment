package com.firman.dicodingevent.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.databinding.ItemHomeEventBinding
import com.firman.dicodingevent.ui.ui.detail.DetailActivity

class HomeEventUpcomingAdapter(
    private var upcomingEvents: List<EventEntity> = emptyList()
) : RecyclerView.Adapter<HomeEventUpcomingAdapter.UpcomingEventViewHolder>() {

    class UpcomingEventViewHolder(private val binding: ItemHomeEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.carouselImageView)

            binding.carouselImageView.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingEventViewHolder {
        val binding = ItemHomeEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingEventViewHolder, position: Int) {
        holder.bind(upcomingEvents[position])
    }

    override fun getItemCount(): Int = upcomingEvents.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEvents(upcoming: List<EventEntity>) {
        upcomingEvents = upcoming.filter { it.active }.take(5)
        notifyDataSetChanged()
    }
}

