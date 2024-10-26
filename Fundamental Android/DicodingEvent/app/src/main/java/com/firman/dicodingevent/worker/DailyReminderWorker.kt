package com.firman.dicodingevent.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.firman.dicodingevent.BuildConfig
import com.firman.dicodingevent.R
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class DailyReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private const val TAG = "DailyReminderWorker"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "Event Reminder"
        const val BASE_URL = BuildConfig.BASE_URL
        const val EVENTS_ENDPOINT = "/events?active=0&limit=1"
    }

    override fun doWork(): Result {
        return try {
            val (name, beginTime) = fetchMostRecentEvent() ?: return Result.failure()
            val title = "Upcoming Event: $name"
            val message = "Event Date: $beginTime"
            showNotification(title, message)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun fetchMostRecentEvent(): Pair<String, String>? {
        Log.d(TAG, "Fetching most recent event...")

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_URL + EVENTS_ENDPOINT)
            .build()

        return try {
            client.newCall(request).execute().use { response ->

                val result = response.body?.string() ?: return null
                Log.d(TAG, result)

                try {
                    val responseObject = JSONObject(result)
                    if (responseObject.getBoolean("error")) {
                        return null
                    }

                    val eventsArray = responseObject.getJSONArray("listEvents")
                    if (eventsArray.length() > 0) {
                        val event = eventsArray.getJSONObject(0)
                        val name = event.getString("name")
                        val beginTime = event.getString("beginTime")
                        Pair(name, beginTime)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: IOException) {
            null
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Channel for event reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
