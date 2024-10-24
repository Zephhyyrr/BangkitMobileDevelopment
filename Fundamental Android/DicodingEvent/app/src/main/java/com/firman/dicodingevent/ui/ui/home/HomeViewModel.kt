package com.firman.dicodingevent.ui.ui.home

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

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>?>()
    val upcomingEvents: MutableLiveData<List<ListEventsItem>?> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun fetchEvents() {
        if (_upcomingEvents.value == null) {
            fetchUpcomingEvents()
        }
        if (_finishedEvents.value == null) {
            fetchFinishedEvents()
        }
    }

    private fun fetchUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(1) // Fetching upcoming events
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.take(5)
                    _upcomingEvents.postValue(events)
                } else {
                    _upcomingEvents.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                _upcomingEvents.postValue(emptyList())
            }
        })
    }


    private fun fetchFinishedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(0) // Fetching finished events
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _finishedEvents.postValue(response.body()?.listEvents?.take(5))
                } else {
                    _finishedEvents.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                _isLoading.value = false
                _finishedEvents.postValue(emptyList())
            }
        })
    }
}
