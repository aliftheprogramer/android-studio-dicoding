package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.alifgantengbanget.databinding.FragmentHomeBinding
import com.example.myapplication.ui.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        // Set layout managers
        binding.recyclerViewActiveEvents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCompletedEvents.layoutManager = LinearLayoutManager(context)

        // Set adapters
        val activeEventsAdapter = EventsAdapter(emptyList())
        val completedEventsAdapter = EventsAdapter(emptyList())

        binding.recyclerViewActiveEvents.adapter = activeEventsAdapter
        binding.recyclerViewCompletedEvents.adapter = completedEventsAdapter

        // Observe data for active events
        viewModel.activeEvents.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                activeEventsAdapter.updateData(it)
            }
        })

        // Observe data for completed events
        viewModel.completedEvents.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                completedEventsAdapter.updateData(it)
            }
        })

        // Observe loading status for active events
        viewModel.isLoadingActive.observe(viewLifecycleOwner, Observer { isLoading ->
            showActiveEventsLoading(isLoading)
        })

        // Observe loading status for completed events
        viewModel.isLoadingCompleted.observe(viewLifecycleOwner, Observer { isLoading ->
            showCompletedEventsLoading(isLoading)
        })

        // Fetch data
        viewModel.fetchActiveEvents()
        viewModel.fetchCompletedEvents()
    }

    private fun showActiveEventsLoading(isLoading: Boolean) {
        binding.progressBarActive.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showCompletedEventsLoading(isLoading: Boolean) {
        binding.progressBarCompleted.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

//    private fun showNoInternetConnection(isNoInternet: Boolean) {
//        binding.noInternetConnection.visibility = if (isNoInternet) View.VISIBLE else View.GONE
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
