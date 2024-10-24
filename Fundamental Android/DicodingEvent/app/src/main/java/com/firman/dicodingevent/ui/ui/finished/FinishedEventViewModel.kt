package com.firman.dicodingevent.ui.ui.finished

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

class FinishedEventViewModel : ViewModel() {

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FinishedEventViewModel"
    }

    init {
        if (_finishedEvent.value == null) {
            findFinishedEvent()
        }
    }

    fun searchEvents(keyword: String) {
        _isLoading.value = true

        val activeCode = if (keyword.isBlank()) 0 else -1

        val client = ApiConfig.getApiService().searchEvents(activeCode, keyword)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(call: Call<DicodingResponse>, response: Response<DicodingResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()
                    val filteredEvents = allEvents.filter { event ->
                        event.name.contains(keyword, ignoreCase = true) ||
                                event.description.contains(keyword, ignoreCase = true)
                    }

                    _finishedEvent.postValue(filteredEvents.ifEmpty { allEvents })
                } else {
                    _finishedEvent.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                _finishedEvent.postValue(emptyList())
                Log.e(TAG, "Failed to fetch events: ${t.message}")
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
                    _finishedEvent.value = emptyList()
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                _finishedEvent.value = emptyList()
                Log.e(TAG, "Failed to fetch events: ${t.message}")
            }
        })
    }
}
