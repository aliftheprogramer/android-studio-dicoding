package com.example.myapplication.ui.comingsoonevent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.alifgantengbanget.databinding.FragmentComingsoonEventBinding
import com.example.myapplication.ui.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ComingsoonEvent : Fragment() {

    private var _binding: FragmentComingsoonEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ComingSoonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComingsoonEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventsAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                adapter.updateData(it)
                showLoading(false) // Hide loading indicator after data is fetched
            }
        })

        showLoading(true) // Show loading indicator before starting data fetch

        viewModel.fetchActiveEvents()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showLoading(true)
                    viewModel.searchEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}