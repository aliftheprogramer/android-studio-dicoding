package com.example.myapplication.data.retrofit

import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.data.response.EventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int = 1
    ): Call<EventsResponse>

    @GET("events")
    fun getCompletedEvents(
        @Query("active") active: Int = 0
    ): Call<EventsResponse>

    @GET("events")
    fun searchActiveEvents(
        @Query("q") query: String,
        @Query("active") active: Int = 1
    ): Call<EventsResponse>

    @GET("events")
    fun searchCompletedEvents(
        @Query("q") query: String,
        @Query("active") active: Int = 0
    ): Call<EventsResponse>

    @GET("events/{id}")
    fun getDetailEvents(@Path("id") eventId: Int): Call<EventResponse>

    @GET("events")
    fun getNearestActiveEvent(
        @Query("active") active: Int = -1,
        @Query("limit") limit: Int = 1
    ): Call<EventsResponse>
}