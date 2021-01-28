package com.hyunkwak.ynews.network

import com.hyunkwak.ynews.utils.API_KEY
import com.hyunkwak.ynews.utils.NUM_OF_RESULTS
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun getQuery(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int = NUM_OF_RESULTS,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getLatestCountryNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int = NUM_OF_RESULTS,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getLatestCategoryNews(
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = NUM_OF_RESULTS,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}