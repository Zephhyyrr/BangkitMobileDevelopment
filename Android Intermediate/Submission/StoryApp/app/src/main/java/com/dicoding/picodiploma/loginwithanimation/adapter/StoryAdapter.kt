package com.dicoding.picodiploma.loginwithanimation.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ListItemBinding
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        val marginInPixels = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.spacing)
        if (position != 0) {
            layoutParams.topMargin = marginInPixels
        } else {
            layoutParams.topMargin = 0
        }
        holder.itemView.layoutParams = layoutParams

        val stories = getItem(position)
        if (stories != null) {
            holder.bind(stories)
        }
    }

    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem) {
            binding.tvItemName.text = stories.name
            binding.tvItemDescription.text = stories.description
            Glide.with(itemView.context)
                .load(stories.photoUrl)
                .into(binding.ivItemPhoto)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.USER_ID, stories.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "profile"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvItemDescription, "description")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    fun removeDivider(recyclerView: RecyclerView) {
        recyclerView.removeItemDecorationAt(0)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
