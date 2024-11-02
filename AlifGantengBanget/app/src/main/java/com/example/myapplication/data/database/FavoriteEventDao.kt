package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent)

    @Update
    fun updateFavoriteEvent(favoriteEvent: FavoriteEvent)

    @Delete
    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent)

    @Query("DELETE FROM FavoriteEvent WHERE id = :eventId")
    fun deleteFavoriteEventById(eventId: Int)

    @Query("SELECT * FROM FavoriteEvent")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id LIMIT 1")
    suspend fun getFavoriteEventByIdSync(id: Int): FavoriteEvent?

    @Query("SELECT * FROM FavoriteEvent WHERE eventName LIKE '%' || :query || '%'")
    fun searchFavoriteEvents(query: String): LiveData<List<FavoriteEvent>>

}