package com.firman.dicodingevent.ui.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiConfig
import com.firman.dicodingevent.ui.ui.upcoming.UpcomingEventViewModel
import com.firman.dicodingevent.ui.ui.upcoming.UpcomingEventViewModel.Companion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventViewModel : ViewModel() {

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FinishedEventViewModel"
    }

    init {
        findFinishedEvent()
    }

    fun searchEvents(keyword: String) {
        _isLoading.value = true

        val activeCode = if (keyword.isBlank()) 0 else -1

        val client = ApiConfig.getApiService().searchEvents(activeCode, keyword)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()

                    if (keyword.isBlank()) {
                        _finishedEvent.postValue(allEvents)
                    } else {
                        val filteredEvents = allEvents.filter { event ->
                            event.name.contains(keyword, ignoreCase = true) ||
                                    event.description.contains(keyword, ignoreCase = true)
                        }

                        if (filteredEvents.isEmpty()) {
                            _finishedEvent.postValue(allEvents)
                        } else {
                            _finishedEvent.postValue(filteredEvents)
                        }
                    }
                } else {
                    Log.e(
                        FinishedEventViewModel.TAG,
                        "Error: ${response.message()}"
                    )
                    _finishedEvent.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(
                    FinishedEventViewModel.TAG,
                    "Failure: ${t.message}"
                )
                _finishedEvent.postValue(emptyList())
            }
        })
    }

    private fun findFinishedEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(0)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val finishedEvents = response.body()?.listEvents ?: emptyList()
                    _finishedEvent.value = finishedEvents
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure: ${t.message}")
            }
        })
    }
}
