package com.firman.dicodingevent.ui.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.response.DicodingResponse
import com.firman.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _finishedEvents = MutableLiveData<Result<List<EventEntity>>>()
    val finishedEvents: LiveData<Result<List<EventEntity>>> = _finishedEvents

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        _finishedEvents.value = Result.Loading // Emit loading state

        // Use Coroutine for non-blocking API call
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getEvents(0).execute() // Synchronous call
                if (response.isSuccessful) {
                    val allEvents = response.body()?.listEvents ?: emptyList()

                    val eventEntities = allEvents.map { event ->
                        EventEntity(
                            id = event.id.toString(),
                            name = event.name,
                            mediaCover = event.mediaCover,
                            isFavorite = false,
                            active = false
                        )
                    }

                    _finishedEvents.postValue(Result.Success(eventEntities))
                } else {
                    Log.e(TAG, "Failed to fetch events: ${response.message()}")
                }
            } catch (e: Exception) {
                _finishedEvents.postValue(Result.Error(e.toString()))
            }
        }
    }

    companion object {
        private const val TAG = "FinishedEventViewModel"
    }
}
