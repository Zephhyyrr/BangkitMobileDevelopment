package com.firman.dicodingevent.ui.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity
import kotlinx.coroutines.launch

class UpcomingEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    val upcomingEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getUpcomingEvents()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            eventRepository.getUpcomingEvents().observeForever { result ->
                if (result is Result.Success || result is Result.Error) {
                    _isLoading.value = false
                }
            }
        }
    }
}
