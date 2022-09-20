package com.example.itunesparsingapiassignment3.di

import com.example.itunesparsingapiassignment3.rest.MusicRepository
import com.example.itunesparsingapiassignment3.rest.MusicRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun providesMusicRepository(
           impl: MusicRepositoryImpl
            ): MusicRepository
}