package com.example.myapplication.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.domain.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MyRepository,
) : ViewModel() {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _isLoadingActive = MutableLiveData<Boolean>()
    val isLoadingActive: LiveData<Boolean> = _isLoadingActive

    fun fetchEvents(id: Int) {
        _isLoadingActive.value = true
        val client = repository.getDetailEvents(id)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _event.value = response.body()?.event
                }
                _isLoadingActive.value = false
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("DetailViewModel", "Failure: ${t.message}")
                _isLoadingActive.value = false
            }
        })
    }
}