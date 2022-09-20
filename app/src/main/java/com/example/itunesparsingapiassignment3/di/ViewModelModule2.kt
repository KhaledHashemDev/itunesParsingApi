package com.example.itunesparsingapiassignment3.di

import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.utils.ClassicViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule2 {

    //For classic songs
    @Provides
    fun provideViewModelFactory2(
        repository2: MusicRepository
    ) : ClassicViewModelFactory = ClassicViewModelFactory(repository2)




}