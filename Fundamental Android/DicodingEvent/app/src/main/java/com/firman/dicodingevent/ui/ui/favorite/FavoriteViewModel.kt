package com.firman.dicodingevent.ui.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.entity.EventEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {

    val favoriteEvents: LiveData<List<EventEntity>> = eventRepository.getFavoriteEvent()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadFavoriteEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            eventRepository.getFavoriteEvent()
            _isLoading.value = false
        }
    }
}
