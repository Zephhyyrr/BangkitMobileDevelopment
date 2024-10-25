package com.firman.dicodingevent.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.entity.EventEntity
import com.firman.dicodingevent.data.Result

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _upcomingEvents = MutableLiveData<Result<List<EventEntity>>>()
    val upcomingEvents: LiveData<Result<List<EventEntity>>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<Result<List<EventEntity>>>()
    val finishedEvents: LiveData<Result<List<EventEntity>>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        loadUpcomingEvents()
        loadFinishedEvents()
    }

    private fun loadUpcomingEvents() {
        _isLoading.value = true // Start loading
        _upcomingEvents.value = Result.Loading

        eventRepository.getUpcomingEvents().observeForever { result ->
            _upcomingEvents.value = result
            if (result is Result.Success || result is Result.Error) {
                _isLoading.value = false // Stop loading when data is received
            }
        }
    }

    private fun loadFinishedEvents() {
        _isLoading.value = true // Start loading
        _finishedEvents.value = Result.Loading

        eventRepository.getFinishedEvents().observeForever { result ->
            _finishedEvents.value = result
            if (result is Result.Success || result is Result.Error) {
                _isLoading.value = false // Stop loading when data is received
            }
        }
    }
}
