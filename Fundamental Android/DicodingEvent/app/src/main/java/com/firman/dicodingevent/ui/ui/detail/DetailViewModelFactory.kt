package com.firman.dicodingevent.ui.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firman.dicodingevent.data.EventRepository

class DetailViewModelFactory(private val eventRepository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
