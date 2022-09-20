package com.example.itunesparsingapiassignment3.di

import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.utils.ClassicViewModelFactory
import com.example.itunesparsingapiassignment3.utils.SongsViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

//For rock songs
    @Provides
    fun provideViewModelFactory(
        repository: MusicRepository
    ):  SongsViewModelFactory = SongsViewModelFactory(repository)


}