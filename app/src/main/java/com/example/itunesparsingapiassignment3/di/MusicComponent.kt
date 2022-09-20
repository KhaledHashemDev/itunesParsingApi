package com.example.itunesparsingapiassignment3.di

import com.example.itunesparsingapiassignment3.MainActivity
import com.example.itunesparsingapiassignment3.utils.ClassicViewModelFactory
import com.example.itunesparsingapiassignment3.utils.PopViewModelFactory
import com.example.itunesparsingapiassignment3.view.ClassicSongsFragment
import com.example.itunesparsingapiassignment3.view.PopSongsFragment
import com.example.itunesparsingapiassignment3.view.RockSongsFragment
import dagger.Component

//modules that we will be using
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ViewModelModule2::class,
        ViewModelModule3::class
    ]
)

interface MusicComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(rockSongsFragment: RockSongsFragment)
    fun inject(classicSongsFragment: ClassicSongsFragment)
    fun inject(classicViewModelFactory: ClassicViewModelFactory)
    fun inject(popSongsFragment: PopSongsFragment)
    fun inject(popViewModelFactory: PopViewModelFactory)

    {

    }

}