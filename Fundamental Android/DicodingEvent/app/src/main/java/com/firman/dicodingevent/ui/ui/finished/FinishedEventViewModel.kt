package com.firman.dicodingevent.ui.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _finishedEvents = MutableLiveData<Result<List<EventEntity>>>()
    val finishedEvents: LiveData<Result<List<EventEntity>>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        loadFinishedEvents()
        fetchFinishedEventsFromApi()
    }

    private fun loadFinishedEvents() {
        _isLoading.value = true

        eventRepository.getFinishedEvents(false).observeForever { result ->
            _finishedEvents.value = result
            _isLoading.value = false
        }
    }

    private fun fetchFinishedEventsFromApi() {
        val client = ApiConfig.getApiService().getEvents(0)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()

                    val eventEntities = allEvents.map { event ->
                        EventEntity(
                            id = event.id.toString(),
                            name = event.name,
                            mediaCover = event.mediaCover,
                            isFavorite = false,
                            active = false
                        )
                    }
                    eventRepository.getFinishedEvents(false)

                    _finishedEvents.postValue(Result.Success(eventEntities))
                } else {
                    Log.e(TAG, "Failed to fetch events: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failed to fetch events: ${t.message}")
            }
        })
    }

//    fun searchEvents(keyword: String) {
//        _isLoading.value = true
//
//        // Pencarian di database
//        eventRepository.getFinishedEvents(0).observeForever { events ->
//            val filteredEvents = events.filter { event ->
//                event.name.contains(keyword, ignoreCase = true) ||
//                        event.description.contains(keyword, ignoreCase = true)
//            }
//            _finishedEvents.postValue(Result.Success(filteredEvents))
//            _isLoading.value = false
//        }
//    }

    companion object {
        private const val TAG = "FinishedEventViewModel"
    }
}
