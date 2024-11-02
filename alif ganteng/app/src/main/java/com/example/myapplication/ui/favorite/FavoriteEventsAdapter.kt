package com.example.myapplication.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
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

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(events[position])
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("eventId", event.id)
            }
            it.findNavController().navigate(R.id.action_favoriteEvent_to_eventDetailFragment, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newEvents: List<FavoriteEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }



}
