package com.example.myapplication.data.repository

import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.data.response.EventsResponse
import com.example.myapplication.data.retrofit.ApiService
import com.example.myapplication.domain.repository.MyRepository
import retrofit2.Call

class MyRepositoryImpl(
    private val apiService: ApiService
): MyRepository {
    override fun getActiveEvents(): Call<EventsResponse> {
        val data = apiService.getActiveEvents()
        return data
    }

    override fun getCompletedEvents(): Call<EventsResponse> {
        val data = apiService.getCompletedEvents()
        return data
    }

    override fun searchEventsCompleted(query: String): Call<EventsResponse> {
        val data = apiService.searchCompletedEvents(query)
        return data
    }

    override fun searchEventsActive(query: String): Call<EventsResponse> {
        val data = apiService.searchActiveEvents(query)
        return data
    }

//    override fun searchEvents(query: String): Call<EventsResponse> {
//        val data = apiService.searchEvents(query)
//        return data
//    }

    override fun getDetailEvents(id : Int): Call<EventResponse> {
        val data = apiService.getDetailEvents(id)
        return data
    }

    override fun getNearestActiveEvent(): Call<EventsResponse> {
        return apiService.getNearestActiveEvent()
    }
}