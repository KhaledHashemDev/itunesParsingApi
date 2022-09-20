package com.example.itunesparsingapiassignment3.di

import android.app.Application

//initializing dagger
class MusicApp  : Application(){

    override fun onCreate() {
        super.onCreate()
       musicComponent = DaggerMusicComponent.builder()
           .applicationModule(ApplicationModule(this))
           .build()
    }

    companion object {
        lateinit var musicComponent: MusicComponent
    }
}