package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.response.EventsResponse
import com.example.myapplication.data.retrofit.ApiConfig
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.EventsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("SameParameterValue")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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


        showLoading(true) // Show loading indicator before starting data fetch
        fetchActiveEvents()
        fetchCompletedEvents()

    }

    private fun fetchActiveEvents() {
        val client = ApiConfig.getApiService().getActiveEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.events?.take(5) ?: listOf()
                    binding.recyclerViewActiveEvents.adapter = EventsAdapter(events)
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                showErrorToast()
            }
        })
    }

    private fun fetchCompletedEvents() {
        val client = ApiConfig.getApiService().getCompletedEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.events?.take(5) ?: listOf()
                    binding.recyclerViewCompletedEvents.adapter = EventsAdapter(events)
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                showErrorToast()
            }
        })
    }

    private fun showErrorToast() {
        Toast.makeText(context, "Failed to fetch events", Toast.LENGTH_SHORT).show()
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