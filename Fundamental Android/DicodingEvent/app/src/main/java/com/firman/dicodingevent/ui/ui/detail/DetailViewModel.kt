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
    private val _eventDetail = MutableLiveData<ListEventsItem?>()
    val eventDetail: LiveData<ListEventsItem?> get() = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val TAG = "DataViewModel"
    }

    fun fetchEventDetail(eventId: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailEvent(eventId)
            .enqueue(object : Callback<DicodingResponse> {
                override fun onResponse(
                    call: Call<DicodingResponse>,
                    response: Response<DicodingResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val eventResponse = response.body()
                        if (eventResponse != null) {
                            Log.d(TAG, "Response: $eventResponse")

                            if (eventResponse.error) {
                                Log.e(TAG, "Error from API: ${eventResponse.message}")
                                _eventDetail.value = null
                                return
                            }
                            val event = eventResponse.event
                            if (event != null) {
                                Log.d(TAG, "Event found: ${event.name}")
                                _eventDetail.value = event
                            } else {
                                Log.e(TAG, "Event is null")
                                _eventDetail.value = null
                            }
                        } else {
                            Log.e(TAG, "Response body is null")
                            _eventDetail.value = null
                        }
                    } else {
                        Log.e(TAG, "Error: ${response.errorBody()?.string()}")
                        _eventDetail.value = null
                    }
                }

                override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "Failure: ${t.message}")
                    _eventDetail.value = null
                }
            })
    }
}