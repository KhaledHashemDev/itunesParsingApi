package com.example.itunesparsingapiassignment3.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesparsingapiassignment3.R
import com.example.itunesparsingapiassignment3.databinding.FragmentRockSongsBinding
import com.example.itunesparsingapiassignment3.model.Songs
import com.example.itunesparsingapiassignment3.viewmodel.SongsViewModel


class RockSongsFragment : Fragment() {

private val binding by lazy {
    FragmentRockSongsBinding.inflate(layoutInflater)
}

//This approach will be the same as passing fragments as the store owner
//private val viewModel: SongsViewModel by viewModels()

//This approach will be the same as passing activity as the store owner (needs fragment nav implement.)
// private val viewModel: SongsViewModel by activityViewModels()

    //instantiating the viewModel
    private val songsViewModel: SongsViewModel by lazy {
/*provides you with the viewModel (viewModel's store owner (RockSongsFragment) needed and factory
factory object will be created for you, default factory is when you have no arguments in the ViewModel
, no arguments to be passed, but if we have arguments, we need to create our own viewModel's factory
 The owner in this case is the fragment
 */
        ViewModelProvider(requireActivity())[SongsViewModel::class.java]
      //  model will be stored in the activity store owner, if you move between fragments, the store
        //owner will be the same
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
return binding.root
    }
}