package com.firman.dicodingevent.ui.ui.finished

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firman.dicodingevent.data.EventRepository
import com.firman.dicodingevent.di.Injection


class FinishedEventModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishedEventViewModel::class.java)) {
            return FinishedEventViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: FinishedEventModelFactory? = null

        fun getInstance(context: Context): FinishedEventModelFactory =
            instance ?: synchronized(this) {
                instance ?: FinishedEventModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}