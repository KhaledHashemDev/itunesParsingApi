package com.example.itunesparsingapiassignment3.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.itunesparsingapiassignment3.adapter.SongsAdapter
import com.example.itunesparsingapiassignment3.databinding.FragmentRockSongsBinding
import com.example.itunesparsingapiassignment3.di.MusicApp
import com.example.itunesparsingapiassignment3.model.domain.DomainSong
import com.example.itunesparsingapiassignment3.utils.Genre
import com.example.itunesparsingapiassignment3.utils.SongsViewModelFactory
import com.example.itunesparsingapiassignment3.utils.UIState
import com.example.itunesparsingapiassignment3.viewmodel.SongsViewModel
import javax.inject.Inject


class RockSongsFragment : Fragment() {

private val binding by lazy {
    FragmentRockSongsBinding.inflate(layoutInflater)
}

    //setting my recycler view
    private val songsAdapter by lazy {
        SongsAdapter {}
        }

@Inject
lateinit var songsViewModelFactory: SongsViewModelFactory


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
        //Creating viewModel factory
        ViewModelProvider(requireActivity(), songsViewModelFactory)[SongsViewModel::class.java]
      //  model will be stored in the activity store owner, if you move between fragments, the store
        //owner will be the same
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //calling Dagger component  to inject the dependencies
        MusicApp.musicComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val swipe : SwipeRefreshLayout = binding.swipeToRefresh




        //binding the recycler view
        binding.rockSongsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            //setting the adapter
            adapter = songsAdapter


        }

        songsViewModel.rockSongs.observe(viewLifecycleOwner) { state->
            when(state)  {
                is UIState.LOADING -> {
                    binding.loadingSpinner.visibility = View.VISIBLE
                    binding.rockSongsRv.visibility = View.GONE
                }
                is UIState.SUCCESS -> {
                    binding.loadingSpinner.visibility = View.GONE
                    binding.rockSongsRv.visibility = View.VISIBLE

                    songsAdapter.updateSongs(state.songs)


                }
                is UIState.ERROR ->{
                    binding.loadingSpinner.visibility = View.GONE
                    binding.rockSongsRv.visibility = View.GONE

                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error occured")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") {dialog, _ ->
                           dialog.dismiss()
                        }
                        .setPositiveButton("Retry"){ dialog, _ ->
                            songsViewModel.getMusic(Genre.ROCK)
                            dialog.dismiss()
                        }
                    //necessary statements to load the dialog
                        .create()
                        .show()
                }
            }

        }

        songsViewModel.getMusic(Genre.ROCK)

        //Adding refresh on swipe property
        swipe.setOnRefreshListener {
            songsViewModel.getMusic(Genre.ROCK)
            songsAdapter.notifyDataSetChanged()
           swipe.isRefreshing = false
        }

        // Inflate the layout for this fragment
return binding.root
    }
}