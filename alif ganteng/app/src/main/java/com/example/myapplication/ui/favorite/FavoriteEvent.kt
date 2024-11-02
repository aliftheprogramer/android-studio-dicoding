package com.example.myapplication.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteEvent : Fragment() {

    private var _binding: FragmentFavoriteEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteEventViewModel by viewModels()
    private lateinit var favoriteEventAdapter: FavoriteEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavoriteEvents()
        setupSearchView()

    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showLoading(true)
                    viewModel.searchFavoriteEvents(it).observe(viewLifecycleOwner, Observer { events ->
                        events?.let {
                            favoriteEventAdapter.updateData(it)
                            showLoading(false)
                        }
                    })
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeFavoriteEvents() {
        viewModel.getAllFavoriteEvents().observe(viewLifecycleOwner, Observer { events ->
            events?.let {
                favoriteEventAdapter.updateData(it)
                showLoading(false)
            }
        })
        showLoading(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun setupRecyclerView() {
        favoriteEventAdapter = FavoriteEventsAdapter(emptyList()) { event ->
            val bundle = Bundle().apply {
                putParcelable("event", event)
            }
            view?.findNavController()?.navigate(R.id.action_favoriteEvent_to_eventDetailFragment, bundle)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteEventAdapter
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}