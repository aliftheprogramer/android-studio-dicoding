package com.dicoding.asclepius.api.services

import com.dicoding.asclepius.api.CancerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CancerApiService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Call<CancerResponse>
}