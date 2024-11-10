package com.dicoding.asclepius.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.database.PredictionData
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<PredictionData, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prediction: PredictionData) {
            Glide.with(binding.historyImage.context)
                .load(Uri.parse(prediction.imageUri))
                .into(binding.historyImage)
            binding.historyLabel.text = prediction.label
            binding.historyScore.text = prediction.score.toString()
            binding.historyInferenceTime.text = "Inference Time: ${prediction.inferenceTime} ms"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PredictionData>() {
        override fun areItemsTheSame(oldItem: PredictionData, newItem: PredictionData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PredictionData, newItem: PredictionData): Boolean {
            return oldItem == newItem
        }
    }
}