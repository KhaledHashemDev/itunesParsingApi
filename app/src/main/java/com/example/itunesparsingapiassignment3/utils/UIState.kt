package com.example.itunesparsingapiassignment3.utils

import com.example.itunesparsingapiassignment3.model.Song


/**
 *Enum class with superpowers
 *A sealed class is part of kotlin, it allows you to
 * restrict the way that you are going to inherit it
 * Success: ready to display data to the user
 * have the UI state as a type of an object, used by accessing
 * the child class.
 * Wrapping those objects/data classes/classes into a sealed class here
 * Variables are the data we are going to need
 * Loading, success and error are just basic states, we can have more states
 */
sealed class UIState {

    //extends the UISTATE
    //singleton in koltin (object keyword)
    object LOADING : UIState()

    data class SUCCESS(val songs: List<Song>) : UIState()

    data  class ERROR(val error: Exception) : UIState()

}