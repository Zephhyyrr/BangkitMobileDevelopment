package com.firman.dicodingevent.ui.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.databinding.ItemFavoriteEventBinding
import com.firman.dicodingevent.ui.ui.detail.DetailActivity

class FavoriteEventAdapter : ListAdapter<EventEntity, FavoriteEventAdapter.FavoriteViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            binding.tvNamaEvent.text = event.name
            Glide.with(binding.ivActivePicture.context)
                .load(event.mediaCover)
                .into(binding.ivActivePicture)

            binding.btnSelengkapnya.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id)
                }
                context.startActivity(intent)
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
