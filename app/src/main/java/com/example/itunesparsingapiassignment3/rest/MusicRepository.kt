package com.example.itunesparsingapiassignment3

import com.example.itunesparsingapiassignment3.model.Song
import com.example.itunesparsingapiassignment3.model.Songs
import com.example.itunesparsingapiassignment3.rest.MusicApi
import com.example.itunesparsingapiassignment3.rest.MusicApi.Companion.serviceApi
import com.example.itunesparsingapiassignment3.utils.FailureResponseFromServer
import com.example.itunesparsingapiassignment3.utils.Genre
import com.example.itunesparsingapiassignment3.utils.NullResponseFromServer
import com.example.itunesparsingapiassignment3.utils.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow


/**Note:
 * View model is only going to be responsible for getting data and receiving data
 * getMusic gets collected in the viewModel
 * UIState allows us to have control/info over the lifecycle, handling all possible
 * states on our application. Allows for better code structure and unidirectional coding
 */

interface MusicRepository {
    //flow will be emitting data continuously to the collector in the
    // view model that will be collecting the data
    //The view is going to be emitting the UIState and doing something
    //will not access data via a string, it will be via an enum class "genre"
fun getMusic(genre: Genre): kotlinx.coroutines.flow.Flow<UIState> //kotlin flows have a coroutine scope but they need to be called form a coroutine scope

}

/**
 * serviceApi is where we are going to get  the network code
 */
class MusicRepositoryImpl(serviceApi: MusicApi) : MusicRepository {

    override fun getMusic(genre: Genre): kotlinx.coroutines.flow.Flow<UIState> = flow {
        emit(UIState.LOADING)

        //Do not add delays on production code
        delay(2000) //delay to demonstrate the loading phase

/*Catching errors in coroutines*/
        try {
            //making the network call
            val response = serviceApi.getMusic(genre.genreName)
            //checking if it's successful
            if (response.isSuccessful){
                //checking the body, if the body is not null
                response.body()?.let {
                    // emit the list  of songs
                    emit(UIState.SUCCESS(it.songs))
                    //if the body of the response is null, throw a null response from server, with custom message
                } ?: throw NullResponseFromServer("Songs are null")  //we created our own custom exceptions in utils folder
            }else{
                //if response is not successful, throw the following custom exception,
                //with the error body as a string
                throw FailureResponseFromServer(response.errorBody()?.string())
            }

        } catch (e: Exception) {
               emit(UIState.ERROR(e))
        }
    }
}

