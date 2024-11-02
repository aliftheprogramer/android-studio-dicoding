package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.EventsResponse
import retrofit2.Call

interface MyRepository {
    fun getActiveEvents(): Call<EventsResponse>
    fun getCompletedEvents(): Call<EventsResponse>
    fun searchEvents(query: String): Call<EventsResponse>
    fun getDetailEvents(id: Int): Call<EventsResponse>
}