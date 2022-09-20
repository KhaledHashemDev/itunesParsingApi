package com.example.itunesparsingapiassignment3.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.viewmodel.PopViewModel

class PopViewModelFactory(
    private val repository3: MusicRepository
): ViewModelProvider.Factory {

    override fun <L : ViewModel> create(modelClass: Class<L>): L {
        return PopViewModel(repository3) as L
    }
}