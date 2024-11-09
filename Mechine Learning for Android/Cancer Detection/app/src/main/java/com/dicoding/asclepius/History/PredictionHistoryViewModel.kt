package com.dicoding.asclepius.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.database.AppDatabase
import com.dicoding.asclepius.database.PredictionHistory
import com.dicoding.asclepius.repository.PredictionHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredictionHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PredictionHistoryRepository
    val allHistory: LiveData<List<PredictionHistory>>

    init {
        val dao = AppDatabase.getDatabase(application).predictionHistoryDao()
        repository = PredictionHistoryRepository(dao)
        allHistory = repository.getAllHistory()
    }

    fun addPredictionHistory(history: PredictionHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPredictionHistory(history)
        }
    }

    fun getAllHistory(): LiveData<List<PredictionHistory>> {
        return allHistory
    }
}
