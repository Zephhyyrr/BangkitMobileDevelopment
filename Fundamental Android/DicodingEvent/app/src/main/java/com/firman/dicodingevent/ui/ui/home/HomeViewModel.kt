package com.firman.dicodingevent.ui.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        fetchEvents()
    }

    fun fetchEvents() {
        fetchUpcomingEvents()
        fetchFinishedEvents()
    }

    private fun fetchUpcomingEvents() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(1) // Assuming 1 is the keyword for upcoming events
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()
                    _upcomingEvents.postValue(allEvents.take(5)) // Limit to 5 upcoming events
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                    _upcomingEvents.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure: ${t.message}")
                _upcomingEvents.postValue(emptyList())
            }
        })
    }

    private fun fetchFinishedEvents() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(0)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()
                    _finishedEvents.postValue(allEvents.take(5))
                } else {
                    Log.e(TAG, "Error: ${response.message()}")
                    _finishedEvents.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure: ${t.message}")
                _finishedEvents.postValue(emptyList())
            }
        })
    }
}
