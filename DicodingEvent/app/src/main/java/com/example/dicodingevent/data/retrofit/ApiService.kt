package com.example.dicodingevent.data.retrofit

import com.example.dicodingevent.data.response.ResponseEvents
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(
        @Query("active") active: Int = 1
    ): Call<ResponseEvents>

    @GET("events")
    fun getCompletedEvents(
        @Query("active") active: Int = 0
    ): Call<ResponseEvents>

    @GET("events")
    fun searchEvents(
        @Query("q") query: String,
        @Query("active") active: Int = -1
    ): Call<ResponseEvents>

    @GET("events")
    fun getDetailEvents(
        @Query("id") id: Int
    ): Call<ResponseEvents>


}