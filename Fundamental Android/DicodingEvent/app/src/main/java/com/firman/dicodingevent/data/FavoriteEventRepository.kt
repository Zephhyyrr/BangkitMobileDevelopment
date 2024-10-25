package com.firman.dicodingevent.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.response.ListEventsItem
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

    fun getUpcomingEvents(active: Boolean = true): LiveData<Result<List<EventEntity>>> {
        val result = MediatorLiveData<Result<List<EventEntity>>>()
        result.value = Result.Loading

        val client = apiService.getEvents(1)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(call: Call<DicodingResponse>, response: Response<DicodingResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    val eventList = ArrayList<EventEntity>()

                    appExecutors.diskIO.execute {
                        events.forEach { event ->
                            val isFavorite = favoriteEventDao.isEventFavorite(event.id.toString())
                            val eventEntity = EventEntity(
                                id = event.id.toString(),
                                name = event.name,
                                mediaCover = event.mediaCover,
                                isFavorite = isFavorite,
                                active = active
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
                    result.value = Result.Error(response.message() ?: "Unknown error")
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        val localData = favoriteEventDao.getAllFavoriteEvents()
        result.addSource(localData) { newData ->
            if (result.value !is Result.Success) {
                result.value = Result.Success(newData.filter { it.active })
            }
        }
        return result
    }

    fun getFinishedEvents(active: Boolean = false): LiveData<Result<List<EventEntity>>> {
        val result = MediatorLiveData<Result<List<EventEntity>>>()
        result.value = Result.Loading

        val client = apiService.getEvents(0) // Mengambil event finished
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(call: Call<DicodingResponse>, response: Response<DicodingResponse>) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    val eventList = ArrayList<EventEntity>()

                    appExecutors.diskIO.execute {
                        events.forEach { event ->
                            val isFavorite = favoriteEventDao.isEventFavorite(event.id.toString())
                            val eventEntity = EventEntity(
                                id = event.id.toString(),
                                name = event.name,
                                mediaCover = event.mediaCover,
                                isFavorite = isFavorite,
                                active = active
                            )
                            eventList.add(eventEntity)
                        }
                        favoriteEventDao.deleteAllNonFavorite()
                        favoriteEventDao.insertEvents(eventList)

                        appExecutors.mainThread.execute {
                            result.value = Result.Success(eventList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                result.value = Result.Error(t.message ?: "Unknown error")
            }
        })

        val localData = favoriteEventDao.getAllFavoriteEvents()
        result.addSource(localData) { newData ->
            if (result.value !is Result.Success) {
                result.value = Result.Success(newData.filter { !it.active })
            }
        }
        return result
    }

    fun getFavoriteEvent(): LiveData<List<EventEntity>> {
        return favoriteEventDao.getFavoriteEvents()
    }

    fun setFavoriteEvent(event: EventEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            event.isFavorite = favoriteState
            favoriteEventDao.updateEvent(event)
        }
    }

    private fun ListEventsItem.toEntity(isFavorite: Boolean, active: Boolean) = EventEntity(
        id = id.toString(),
        name = name,
        mediaCover = mediaCover,
        isFavorite = isFavorite,
        active = active
    )

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
                    .also { instance = it }
            }
    }
}
