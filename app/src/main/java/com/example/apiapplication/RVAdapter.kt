package com.example.apiapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapplication.databinding.StarWarsLayoutBinding

class RVAdapter : RecyclerView.Adapter<RVAdapter.RVAdapterViewHolder>() {
    var onItemClick: ((ResultSW) -> Unit)? = null

    inner class RVAdapterViewHolder(val binding: StarWarsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ResultSW>() {
        override fun areItemsTheSame(oldItem: ResultSW, newItem: ResultSW): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ResultSW, newItem: ResultSW): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var starWars: StarWars? = null
        set(value) {
            field = value
            differ.submitList(value?.results)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapterViewHolder {
        return RVAdapterViewHolder(
            StarWarsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RVAdapterViewHolder, position: Int) {
        holder.binding.apply {
            val starWar = differ.currentList[position]
            characterName.text = starWar.name
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(starWar)
            }
        }

    }
}

