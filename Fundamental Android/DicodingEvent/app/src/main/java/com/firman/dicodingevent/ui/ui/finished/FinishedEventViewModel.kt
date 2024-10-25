package com.firman.dicodingevent.ui.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.data.Result
import com.firman.dicodingevent.data.entity.EventEntity

class FinishedEventViewModel(eventRepository: EventRepository) : ViewModel() {
    val finishedEvents: LiveData<Result<List<EventEntity>>> = eventRepository.getFinishedEvents(active = false)
}
