package com.firman.dicodingevent.ui.ui.detail

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

class DetailViewModel : ViewModel() {

    private val _eventDetail = MutableLiveData<ListEventsItem?>() // Menyimpan detail event
    val eventDetail: LiveData<ListEventsItem?> = _eventDetail // LiveData untuk observasi detail event

    private val _isLoading = MutableLiveData<Boolean>() // Menyimpan status loading
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun fetchEventDetail(eventId: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailEvent(eventId) // Mengambil detail event dari API
        client.enqueue(object : Callback<DicodingResponse> {
            override fun onResponse(
                call: Call<DicodingResponse>,
                response: Response<DicodingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val eventDetailList = response.body()?.listEvents
                    if (!eventDetailList.isNullOrEmpty()) {
                        _eventDetail.value = eventDetailList[0] // Set eventDetail dengan item pertama dari list
                    } else {
                        Log.e(TAG, "Event detail is null or empty")
                    }
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


