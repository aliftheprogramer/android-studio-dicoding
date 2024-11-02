package com.example.myapplication.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemEventsBinding
import com.example.myapplication.data.database.FavoriteEvent

class FavoriteEventsAdapter(
    private var events: List<FavoriteEvent>,
    private val onItemClick: (FavoriteEvent) -> Unit
    ) : RecyclerView.Adapter<FavoriteEventsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent) {
            binding.eventName.text = event.eventName
            binding.eventSummary.text = event.eventSummary
            Glide.with(binding.imageLogo.context)
                .load(event.imageLogo)
                .into(binding.imageLogo)
            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newEvents: List<FavoriteEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }



}
