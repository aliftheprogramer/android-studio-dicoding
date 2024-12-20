package com.example.myapplication.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R

class FavoriteEvent : Fragment() {

    companion object {
        fun newInstance() = FavoriteEvent()
    }

    private val viewModel: FavoriteEventViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite_event, container, false)
    }
}