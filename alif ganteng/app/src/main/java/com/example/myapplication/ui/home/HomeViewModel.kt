package com.example.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventsResponse
import com.example.myapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _activeEvents = MutableLiveData<List<Event>>()
    val activeEvents: LiveData<List<Event>> = _activeEvents

    private val _completedEvents = MutableLiveData<List<Event>>()
    val completedEvents: LiveData<List<Event>> = _completedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchActiveEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getActiveEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _activeEvents.value = response.body()?.events?.take(5) ?: listOf()
                } else {
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                    _activeEvents.value = listOf()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failure: ${t.message}")
                _activeEvents.value = listOf()
                _isLoading.value = false
            }
        })
    }

    fun fetchCompletedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCompletedEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _completedEvents.value = response.body()?.events?.take(5) ?: listOf()
                } else {
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                    _completedEvents.value = listOf()
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failure: ${t.message}")
                _completedEvents.value = listOf()
                _isLoading.value = false
            }
        })
    }
}