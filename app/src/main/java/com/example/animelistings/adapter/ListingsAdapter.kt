package com.example.animelistings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animelistings.R
import com.example.animelistings.databinding.ListAnimeItemBinding
import com.example.animelistings.domain.Anime

class ListingsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Anime, ListingsAdapter.AnimeViewHolder>(DiffCallback()) {
    class AnimeViewHolder(private val binding: ListAnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            binding.anime = anime
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(ListAnimeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(anime)
        }
        holder.bind(anime)
    }

    class OnClickListener(val clickListener: (anime: Anime) -> Unit) {
        fun onClick(anime: Anime) {
            clickListener(anime)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Anime>() {
    override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
        return oldItem == newItem
    }
}
