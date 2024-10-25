package com.firman.dicodingevent.ui.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity

class UpcomingEventViewModel(eventRepository: EventRepository) : ViewModel() {
    val upcomingEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getUpcomingEvents()
}
