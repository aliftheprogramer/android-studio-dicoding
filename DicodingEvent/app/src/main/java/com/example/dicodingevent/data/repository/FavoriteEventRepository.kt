package com.example.dicodingevent.data.repository

import androidx.lifecycle.LiveData
import com.example.dicodingevent.data.database.FavoriteEvent
import com.example.dicodingevent.data.database.FavoriteEventDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class FavoriteEventRepository @Inject constructor(
    private val mFavoriteEventDao: FavoriteEventDao
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = mFavoriteEventDao.getAllFavoriteEvents()

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent> = mFavoriteEventDao.getFavoriteEventById(id)

    // Tambahkan fungsi ini
    suspend fun getFavoriteEventByIdSync(id: Int): FavoriteEvent? = mFavoriteEventDao.getFavoriteEventByIdSync(id)

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.insertFavoriteEvent(favoriteEvent) }
    }

    fun update(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.updateFavoriteEvent(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.deleteFavoriteEvent(favoriteEvent) }
    }

    fun deleteFavoriteEventById(eventId: Int) {
        executorService.execute { mFavoriteEventDao.deleteFavoriteEventById(eventId) }
    }

    fun searchFavoriteEvents(query: String): LiveData<List<FavoriteEvent>> = mFavoriteEventDao.searchFavoriteEvents(query)


}