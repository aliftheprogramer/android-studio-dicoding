package com.example.dicodingevent.data.repository

import com.example.dicodingevent.data.response.ResponseEvents
import com.example.dicodingevent.data.retrofit.ApiService
import com.example.dicodingevent.domain.repository.MyRepository
import retrofit2.Call

class MyRepositoryImpl(
    private val apiService: ApiService
): MyRepository {
    override fun getActiveEvents(): Call<ResponseEvents> {
        val data = apiService.getActiveEvents()
        return data
    }

    override fun getCompletedEvents(): Call<ResponseEvents> {
        val data = apiService.getCompletedEvents()
        return data
    }

    override fun getDetailEvents(id: Int): Call<ResponseEvents> {
        TODO("Not yet implemented")
    }

    override fun searchEvents(query: String): Call<ResponseEvents> {
        val data = apiService.searchEvents(query)
        return data
    }


}