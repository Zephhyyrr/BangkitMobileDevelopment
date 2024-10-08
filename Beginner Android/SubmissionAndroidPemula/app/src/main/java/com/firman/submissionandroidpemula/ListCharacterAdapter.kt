package com.firman.submissionandroidpemula

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListCharacterAdapter(private val listCharacter: ArrayList<Character>) : RecyclerView.Adapter<ListCharacterAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null // Changed to nullable

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_character, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val character = listCharacter[position]

        holder.imgPhoto.setImageResource(character.photo)
        holder.tvName.text = character.name
        holder.tvDescription.text = character.description

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(character)
        }
    }

    override fun getItemCount(): Int = listCharacter.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Character)
    }
}
