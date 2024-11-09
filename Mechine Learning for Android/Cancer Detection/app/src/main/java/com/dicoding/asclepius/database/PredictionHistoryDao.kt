package com.dicoding.asclepius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PredictionHistoryDao {

    @Insert
    suspend fun insert(history: PredictionHistory)

    @Query("SELECT * FROM prediction_history ORDER BY timestamp DESC")
    fun getAllHistory(): LiveData<List<PredictionHistory>>
}
