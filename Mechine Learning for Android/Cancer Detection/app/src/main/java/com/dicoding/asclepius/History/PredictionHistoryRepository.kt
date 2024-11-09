package com.dicoding.asclepius.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.PredictionHistory
import com.dicoding.asclepius.database.PredictionHistoryDao

class PredictionHistoryRepository(private val dao: PredictionHistoryDao) {

    suspend fun insertPredictionHistory(history: PredictionHistory) {
        dao.insert(history)
    }

    // Perbaiki fungsi ini agar mengembalikan LiveData
    fun getAllHistory(): LiveData<List<PredictionHistory>> {
        return dao.getAllHistory()
    }
}
