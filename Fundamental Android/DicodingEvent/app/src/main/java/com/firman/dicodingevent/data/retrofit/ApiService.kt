package com.firman.dicodingevent.data.retrofit

import com.firman.dicodingevent.data.response.DicodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int,
        @Query("limit") limit: Int? = null
    ): Call<DicodingResponse>

    @GET("events")
    fun searchEvents(
        @Query("active") active: Int,
        @Query("keyword") keyword: String? = null
    ): Call<DicodingResponse>


    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DicodingResponse>
}