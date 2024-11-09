package com.dicoding.asclepius.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prediction_history")
data class PredictionHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageUri: String,
    val resultLabel: String,
    val confidenceScore: String,
    val timestamp: Long = System.currentTimeMillis()
)