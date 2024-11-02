package com.example.myapplication.ui.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.FavoriteEvent
import com.example.myapplication.data.repository.FavoriteEventRepository
import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.EventsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FavoriteEventViewModel @Inject constructor(
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {

//    private val _favoriteEvent = MutableLiveData<FavoriteEvent>()
//    val favoriteEvent: LiveData<FavoriteEvent> = _favoriteEvent


    suspend fun isFavoriteEvent(eventId: Int): Boolean {
        return favoriteEventRepository.getFavoriteEventByIdSync(eventId) != null
    }


    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        favoriteEventRepository.getAllFavoriteEvents()

//    fun getFavoriteEventById(id: Int) {
//        val data = favoriteEventRepository.getFavoriteEventById(id)
//        _favoriteEvent.value = data.value
//    }
    fun getFavoriteEventById(id: Int) : LiveData<FavoriteEvent> =
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