package com.firman.dicodingevent.ui.ui.finished

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.databinding.ItemHomeEventFinishedBinding
import com.firman.dicodingevent.ui.ui.detail.DetailActivity

class HomeEventFinishedAdapter(
    private var finishedEvents: List<EventEntity> = emptyList()
) : RecyclerView.Adapter<HomeEventFinishedAdapter.FinishedEventViewHolder>() {

    class FinishedEventViewHolder(private val binding: ItemHomeEventFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity) {
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imgItemPhoto)

            binding.tvItemName.text = event.name

            binding.cardView.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra("EVENT_ID", event.id)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedEventViewHolder {
        val binding = ItemHomeEventFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishedEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishedEventViewHolder, position: Int) {
        holder.bind(finishedEvents[position])
    }

    override fun getItemCount(): Int = finishedEvents.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateEvents(finished: List<EventEntity>) {
        finishedEvents = finished.filter { !it.active }.take(5)
        notifyDataSetChanged()
    }
}
