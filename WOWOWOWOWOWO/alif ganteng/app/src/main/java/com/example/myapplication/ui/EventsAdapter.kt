package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.Event

class EventsAdapter(private var events: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("event", event)
            }
            it.findNavController().navigate(R.id.action_completedEvent_to_eventDetailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = events.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newEvents: List<Event>) {
        Log.d("EventsAdapter", "Updating data with ${newEvents.size} events")
        events = newEvents
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageLogo: ImageView = itemView.findViewById(R.id.imageLogo)
        private val eventName: TextView = itemView.findViewById(R.id.eventName)
        private val eventSummary: TextView = itemView.findViewById(R.id.eventSummary)

        fun bind(event: Event) {
            Log.d("EventViewHolder", "Binding event: ${event.name}")
            eventName.text = event.name
            eventSummary.text = Html.fromHtml(event.summary, Html.FROM_HTML_MODE_LEGACY)
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .error(R.drawable.ic_error) // Placeholder for error
                .into(imageLogo)
        }
    }
}