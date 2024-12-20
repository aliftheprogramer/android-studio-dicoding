package com.dicoding.mydatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    override fun <T:ViewModel> create (modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }
}