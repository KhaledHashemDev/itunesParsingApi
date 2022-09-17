package com.example.itunesparsingapiassignment3.di

import dagger.Component

//modules that we will be using
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class
    ]
)

interface MusicComponent {


}