package com.example.itunesparsingapiassignment3.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itunesparsingapiassignment3.MusicRepository
import com.example.itunesparsingapiassignment3.utils.Genre
import com.example.itunesparsingapiassignment3.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * We can have as many viewModel(s) as we want, that can communicate to the views
 * Each feature in an app can have its own viewModel. It's a case by case
 * Extending the viewModel Class ensures that we want this to have the behaviour of a viewModel
 */

private const val TAG = "SongsViewModel"

/**
 * First thing we need to get the info is the repository
 */
class SongsViewModel(
    private val repository: MusicRepository
) : ViewModel() {

    /** Live Data - lifecycle aware, it will know when to emit the data  and when
     * not to emit the data.
     * It's an observable that emits data to the view
     * Lifecycle owner(ViewModel) and observer need to be known, the view needs to know
     * the UIState
     * RxJava is not lifecycle aware
     */
    //backfield variable - a set of two variables of the same type, one can be set, and the
    //  other will be the get(ter) , one is private and the other is public.
    //Mutable  livedata can change the value but it will be changing in the viewModel only
    private val _rockSongs: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    //LiveData object is an immutable variable that will get the value from the mutable and
   // then emit that to the view, main purpose of it, your VIEW should be enabled to change LIVE
    //DATA state or value. This is a read only variable
    val rockSongs: LiveData<UIState> get() = _rockSongs

    init{
// here you can perform some operation once the ViewModel gets created


        Log.d(TAG, "ViewModel  INIT: ViewModel created")
    }

    /* Calling this method from the repo to make the network call
    but since it's a flow, we need to call on collect to retrieve the data
    from the flow, function's body has to be in a 'CoroutineScope,Dispatcher
    .IO is a thread that the body of the function will run, launch is
     the builder of the Coroutine (Coroutine vs viewModelScope(runs in the main thread
     by default, need to declare Dispatchers.IO) */
     fun getMusic(genre: Genre) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMusic(genre).collect {

/* set (it) vs postValue -  postValue posts the value in the main thread as well as in
the worker thread, always. Set value, directly sets the value to the main thread */
    withContext(Dispatchers.Main) { //HERE WE CHANGED TO THE MAIN THREAD (INSTEAD OF THE IO THREAD)
                    //_rockSongs.value = it
        //Set value from the LIVE DATA needs to be called in the MAIN THREAD
                }
// post value form the LIVE DATA can be called in the main thread as well as the worker thread
// This will keep only the last value emitted, and will be slower than the set value
                _rockSongs.postValue(it)  //us this if not sure (most updated data)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel got cleared")
    }



}