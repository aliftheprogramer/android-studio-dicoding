package com.example.myapplication.data.repository

import com.example.myapplication.data.retrofit.ApiService
import com.example.myapplication.domain.repository.MyRepository

class MyRepositoryImpl(
    private val apiService: ApiService,
): MyRepository {
    override fun getActiveEvents() = apiService.getActiveEvents()

    override fun getCompletedEvents() = apiService.getCompletedEvents()

    override fun searchEvents(query: String) = apiService.searchEvents(query)

    override fun getDetailEvents(id : Int) = apiService.getDetailEvents(id)
}