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

                            if (eventResponse.error) {
                                _eventDetail.value = null
                                return
                            }
                            val event = eventResponse.event
                            if (event != null) {
                                _eventDetail.value = event
                            } else {
                                _eventDetail.value = null
                            }
                        } else {
                            _eventDetail.value = null
                        }
                    } else {
                        _eventDetail.value = null
                    }
                }

                override fun onFailure(call: Call<DicodingResponse>, t: Throwable) {
                    _isLoading.value = false
                    _eventDetail.value = null
                }
            })
    }
}