package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.data.response.EventsResponse
import retrofit2.Call

interface MyRepository {
    fun getActiveEvents(): Call<EventsResponse>
    fun getCompletedEvents(): Call<EventsResponse>
    fun searchEventsCompleted(query: String): Call<EventsResponse>
    fun searchEventsActive(query: String): Call<EventsResponse>
    fun getDetailEvents(id: Int): Call<EventResponse>
    fun getNearestActiveEvent(): Call<EventsResponse>
}