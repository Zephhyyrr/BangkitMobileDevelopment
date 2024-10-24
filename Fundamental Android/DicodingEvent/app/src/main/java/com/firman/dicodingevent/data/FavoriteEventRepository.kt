package com.firman.dicodingevent.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.firman.dicodingevent.BuildConfig
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.retrofit.ApiService
import com.firman.dicodingevent.database.FavoriteEventDao
import com.firman.dicodingevent.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<EventEntity>>>()

    fun getFavoriteEvents(): LiveData<Result<List<EventEntity>>> {
        result.value = Result.Loading
        val client = apiService.getEvents(BuildConfig.BASE_URL) // Make sure BASE_URL is defined in build.gradle
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents
                    val eventList = ArrayList<EventEntity>()
                    appExecutors.diskIO.execute {
                        events?.forEach { event ->
                            val isFavorite = favoriteEventDao.isEventFavorite(event.id.toString())
                            val eventEntity = EventEntity(
                                event.id.toString(),  // Convert Int to String
                                event.name,
                                event.mediaCover,
                                isFavorite
                            )
                            eventList.add(eventEntity)
                        }
                        favoriteEventDao.deleteAllNonFavorite()
                        favoriteEventDao.insertEvents(eventList)
                        appExecutors.mainThread.execute {
                            result.value = Result.Success(eventList)
                        }
                    }
                } else {
                    result.value = Result.Error("Failed to fetch events")
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })
        return result
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteEventDao: FavoriteEventDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, favoriteEventDao, appExecutors)
            }
    }
}
