package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.EventsAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewActiveEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCompletedEvents.layoutManager = LinearLayoutManager(context)

        val activeEventsAdapter = EventsAdapter(emptyList())
        val completedEventsAdapter = EventsAdapter(emptyList())

        binding.recyclerViewActiveEvents.adapter = activeEventsAdapter
        binding.recyclerViewCompletedEvents.adapter = completedEventsAdapter

        viewModel.activeEvents.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                activeEventsAdapter.updateData(it)
            }
        })

        viewModel.completedEvents.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                completedEventsAdapter.updateData(it)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            showLoading(isLoading)
        })

        viewModel.fetchActiveEvents()
        viewModel.fetchCompletedEvents()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}