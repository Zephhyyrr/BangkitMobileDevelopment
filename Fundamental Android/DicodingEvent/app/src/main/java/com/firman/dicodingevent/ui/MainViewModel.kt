package com.firman.dicodingevent.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _activeEvent = MutableLiveData<List<ListEventsItem>>()
    val activeEvent: LiveData<List<ListEventsItem>> = _activeEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findActiveEvent()
    }

    private fun findActiveEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(1)
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>)
            {
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
