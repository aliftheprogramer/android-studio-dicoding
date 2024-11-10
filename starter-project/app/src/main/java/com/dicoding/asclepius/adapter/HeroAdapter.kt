package com.dicoding.asclepius.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.api.ArticlesItem
import com.dicoding.asclepius.api.CancerResponse
import com.dicoding.asclepius.databinding.ItemHeroBinding

class HeroAdapter(private val articles: List<ArticlesItem>) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>(){
    inner class HeroViewHolder(private val binding: ItemHeroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.heroTitle.text = article.title
            binding.heroLink.text = article.url
            Glide.with(binding.heroImage.context)
                .load(article.urlToImage)
                .into(binding.heroImage)

            binding.heroLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                binding.root.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(articles[position])
    }

}