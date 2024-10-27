package com.firman.dicodingevent.ui.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity
import kotlinx.coroutines.launch

class FinishedEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    val finishedEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getFinishedEvents(active = false)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchFinishedEvents()
    }

    private fun fetchFinishedEvents() {
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
