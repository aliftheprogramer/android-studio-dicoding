package com.example.dicodingevent.domain.repository

import com.example.dicodingevent.data.response.ResponseEvents
import retrofit2.Call

interface MyRepository {
    fun getActiveEvents(): Call<ResponseEvents>
    fun getCompletedEvents(): Call<ResponseEvents>
    fun getDetailEvents(id: Int): Call<ResponseEvents>
    fun searchEvents(query: String): Call<ResponseEvents>
}