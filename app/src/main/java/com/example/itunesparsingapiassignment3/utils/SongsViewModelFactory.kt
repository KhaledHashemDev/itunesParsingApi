package com.example.itunesparsingapiassignment3.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesparsingapiassignment3.MusicRepository
import com.example.itunesparsingapiassignment3.viewmodel.SongsViewModel

//Instantiate it with the repo when needed
class SongsViewModelFactory(
    private val repository: MusicRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SongsViewModel(repository) as T
    }
}