package com.example.itunesparsingapiassignment3.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.view.ClassicSongsFragment
import com.example.itunesparsingapiassignment3.viewmodel.ClassicViewModel

class ClassicViewModelFactory(
    private val repository2: MusicRepository
): ViewModelProvider.Factory {

    override fun <K : ViewModel> create(modelClass: Class<K>): K {
        return ClassicViewModel(repository2) as K
    }
}
