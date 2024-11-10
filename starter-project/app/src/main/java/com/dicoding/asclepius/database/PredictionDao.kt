package com.dicoding.asclepius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface PredictionDao {
    @Insert
        fun insert(prediction: PredictionData)


    @Query("DELETE FROM prediction_data")
    fun deleteAllPredictions()

    @Query("SELECT * FROM prediction_data")
         fun getAllPredictions(): LiveData<List<PredictionData>>
}