package com.firman.dicodingevent.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiService
import com.firman.dicodingevent.util.NotificationHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.await

class DailyReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override fun doWork(): Result {
        return try {
            val event = fetchUpcomingEvent() ?: return Result.failure()
            sendNotification(event)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun fetchUpcomingEvent(): ListEventsItem? {
        return try {
            val response = apiService.getEvents(active = 1).execute()
            if (response.isSuccessful) {
                response.body()?.listEvents?.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun sendNotification(event: ListEventsItem) {
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.sendReminderNotification(event.name, event.endTime)
    }
}
