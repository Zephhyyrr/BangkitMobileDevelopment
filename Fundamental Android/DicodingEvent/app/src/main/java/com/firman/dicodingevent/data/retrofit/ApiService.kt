package com.firman.dicodingevent.data.retrofit

import com.firman.dicodingevent.data.response.DicodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<DicodingResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): Call<DicodingResponse>
}