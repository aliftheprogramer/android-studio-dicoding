package com.dicoding.asclepius.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prediction_data")
data class PredictionData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val label: String,
    val score: Float,
    val inferenceTime: Long
)