package com.example.myapplication.ui.completed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventsResponse
import com.example.myapplication.di.NetworkModule
import com.example.myapplication.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CompletedEventViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun fetchCompletedEvents() {
        val client = repository.getCompletedEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _events.value = response.body()?.events
                } else {
                    Log.e("CompletedEventViewModel", "Error: ${response.errorBody()?.string()}")
                    _events.value = emptyList()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("CompletedEventViewModel", "Failure: ${t.message}")
                _events.value = emptyList()
            }
        })
    }

    fun searchEvents(query: String) {
        val client = NetworkModule.provideApiService().searchEvents(query, 0)
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _events.value = response.body()?.events
                } else {
                    Log.e("CompletedEventViewModel", "Error: ${response.errorBody()?.string()}")
                    _events.value = emptyList()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("CompletedEventViewModel", "Failure: ${t.message}")
                _events.value = emptyList()
            }
        })
    }
}