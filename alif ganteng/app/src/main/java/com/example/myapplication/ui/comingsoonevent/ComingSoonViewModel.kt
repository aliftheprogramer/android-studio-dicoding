package com.example.myapplication.ui.comingsoonevent

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

class ComingSoonViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun fetchActiveEvents() {
        val client = ApiConfig.getApiService().getActiveEvents()
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _events.value = response.body()?.events
                } else {
                    Log.e("ComingSoonViewModel", "Error: ${response.errorBody()?.string()}")
                    _events.value = emptyList()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("ComingSoonViewModel", "Failure: ${t.message}")
                _events.value = emptyList()
            }
        })
    }

    fun searchEvents(query: String) {
        val client = ApiConfig.getApiService().searchEvents(query, 1)
        client.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful) {
                    _events.value = response.body()?.events
                } else {
                    Log.e("ComingSoonViewModel", "Error: ${response.errorBody()?.string()}")
                    _events.value = emptyList()
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.e("ComingSoonViewModel", "Failure: ${t.message}")
                _events.value = emptyList()
            }
        })
    }
}