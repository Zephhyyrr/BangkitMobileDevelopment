package com.firman.dicodingevent.ui.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.response.ListEventsItem
import com.firman.dicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventDetail = MutableLiveData<ListEventsItem?>()
    val eventDetail: LiveData<ListEventsItem?> get() = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getFavoriteEvent() = eventRepository.getFavoriteEvent()

    fun saveFavorite(event: EventEntity) {
        eventRepository.setFavoriteEvent(event, true)
        _isFavorite.value = true
    }

    fun deleteFavorite(event: EventEntity) {
        eventRepository.setFavoriteEvent(event, false)
        _isFavorite.value = false
    }

    fun fetchEventDetail(eventId: String) {
        if (_eventDetail.value == null) {
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
                                _eventDetail.value = eventResponse.event
                                checkIfFavorite(eventResponse.event?.id.toString()) // Check if the event is favorite
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

    private fun checkIfFavorite(eventId: String) {
        // Check favorite status from the repository
        eventRepository.getFavoriteEvent().observeForever { favorites ->
            _isFavorite.value = favorites.any { it.id == eventId }
        }
    }
}
