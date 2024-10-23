package com.dicoding.mynoteapps.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.mynoteapps.database.Note
import com.dicoding.mynoteapps.repository.NoteRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}