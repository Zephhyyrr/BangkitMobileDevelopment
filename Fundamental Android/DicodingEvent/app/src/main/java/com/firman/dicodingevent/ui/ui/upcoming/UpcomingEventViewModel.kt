package com.firman.dicodingevent.ui.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingEventViewModel : ViewModel() {

    private val _activeEvent = MutableLiveData<List<ListEventsItem>>()
    val activeEvent: LiveData<List<ListEventsItem>> = _activeEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingEventViewModel"
    }

    init {
        findActiveEvent()
    }

    fun searchEvents(keyword: String) {
        _isLoading.value = true

        val activeCode = if (keyword.isBlank()) 1 else -1

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
                        _activeEvent.postValue(allEvents)
                    } else {
                        val filteredEvents = allEvents.filter { event ->
                            event.name.contains(keyword, ignoreCase = true) ||
                                    event.description.contains(keyword, ignoreCase = true)
                        }

                        if (filteredEvents.isEmpty()) {
                            _activeEvent.postValue(allEvents)
                        } else {
                            _activeEvent.postValue(filteredEvents)
                        }
                    }
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                    _activeEvent.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure: ${t.message}")
                _activeEvent.postValue(emptyList())
            }
        })
    }

    private fun findActiveEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(1)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val activeEvents = response.body()?.listEvents ?: emptyList()
                    _activeEvent.postValue(activeEvents)
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
