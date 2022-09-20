package com.example.itunesparsingapiassignment3.di

import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.utils.PopViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule3 {


    //For pop songs
    @Provides
    fun provideViewModelFactory3(
        repository3: MusicRepository
    ) : PopViewModelFactory = PopViewModelFactory(repository3)


}