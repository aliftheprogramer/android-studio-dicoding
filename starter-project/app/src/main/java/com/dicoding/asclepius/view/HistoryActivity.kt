package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HeroAdapter
import com.dicoding.asclepius.adapter.HistoryAdapter
import com.dicoding.asclepius.animation.FadeInItemAnimator
import com.dicoding.asclepius.database.AppDatabase
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.let { rootView ->
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        historyAdapter = HistoryAdapter()
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.itemAnimator = FadeInItemAnimator() // Set custom ItemAnimator

        loadHistory()

        binding.deleteAllButton.setOnClickListener{
            deleteAllHistory()
        }

    }

    private fun deleteAllHistory() {
        val database = AppDatabase.getDatabase(this)
        lifecycleScope.launch(Dispatchers.IO) {
            database.predictionDao().deleteAllPredictions()
        }
    }
    private fun loadHistory() {
        val database = AppDatabase.getDatabase(this)
        database.predictionDao().getAllPredictions().observe(this, Observer { predictions ->
            historyAdapter.submitList(predictions)
        })
    }

}