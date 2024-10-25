package com.firman.dicodingevent.ui.ui.upcoming

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.di.Injection

class UpcomingEventModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingEventViewModel::class.java)) {
            return UpcomingEventViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: UpcomingEventModelFactory? = null

        fun getInstance(context: Context): UpcomingEventModelFactory =
            instance ?: synchronized(this) {
                instance ?: UpcomingEventModelFactory(Injection.provideRepository(context)).also { instance = it }
            }
    }
}
