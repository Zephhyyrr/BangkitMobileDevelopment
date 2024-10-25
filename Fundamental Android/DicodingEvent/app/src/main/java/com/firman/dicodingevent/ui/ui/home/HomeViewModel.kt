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

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        loadUpcomingEvents()
        loadFinishedEvents()
    }

    private fun loadUpcomingEvents() {
        _upcomingEvents.value = Result.Loading
        eventRepository.getUpcomingEvents().observeForever { result ->
            _upcomingEvents.value = result
        }
    }

    private fun loadFinishedEvents() {
        _finishedEvents.value = Result.Loading
        eventRepository.getFinishedEvents().observeForever { result ->
            _finishedEvents.value = result
        }
    }
}

