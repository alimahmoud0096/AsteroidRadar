package com.udacity.asteroidradar.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.RawItemAsteroidsBinding
import com.udacity.asteroidradar.domain.Asteroid


class AsteroidsAdapter(var asteroidClickListener: AsteroidClickListener) : ListAdapter<Asteroid, AsteroidsAdapter.ItemViewholder>(DiffCallback()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            RawItemAsteroidsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ,asteroidClickListener)

    }

    override fun onBindViewHolder(holder: AsteroidsAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(
        val binding: RawItemAsteroidsBinding,
        val asteroidClickListener: AsteroidClickListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid) = with(itemView) {
            binding.asteroid = item
            binding.clickListner = asteroidClickListener
            binding.executePendingBindings()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}