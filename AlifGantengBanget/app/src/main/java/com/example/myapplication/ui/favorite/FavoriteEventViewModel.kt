package com.example.myapplication.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.FavoriteEvent
import com.example.myapplication.data.repository.FavoriteEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteEventViewModel @Inject constructor(
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {

    suspend fun isFavoriteEvent(eventId: Int): Boolean {
        return favoriteEventRepository.getFavoriteEventByIdSync(eventId) != null
    }


    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        favoriteEventRepository.getAllFavoriteEvents()

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent> =
        favoriteEventRepository.getFavoriteEventById(id)

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteEventRepository.insert(favoriteEvent)
        }
    }

    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteEventRepository.delete(favoriteEvent)
        }
    }

    fun deleteFavoriteEventById(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteEventRepository.deleteFavoriteEventById(eventId)
        }
    }

    fun searchFavoriteEvents( query : String): LiveData<List<FavoriteEvent>> =
        favoriteEventRepository.searchFavoriteEvents(query)




}