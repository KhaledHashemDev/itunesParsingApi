package com.example.itunesparsingapiassignment3.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.itunesparsingapiassignment3.R
import com.example.itunesparsingapiassignment3.adapter.PopsAdapter
import com.example.itunesparsingapiassignment3.adapter.SongsAdapter
import com.example.itunesparsingapiassignment3.databinding.FragmentPopSongsBinding
import com.example.itunesparsingapiassignment3.databinding.FragmentRockSongsBinding
import com.example.itunesparsingapiassignment3.di.MusicApp
import com.example.itunesparsingapiassignment3.di.ViewModelModule
import com.example.itunesparsingapiassignment3.utils.Genre
import com.example.itunesparsingapiassignment3.utils.PopViewModelFactory
import com.example.itunesparsingapiassignment3.utils.SongsViewModelFactory
import com.example.itunesparsingapiassignment3.utils.UIState
import com.example.itunesparsingapiassignment3.viewmodel.PopViewModel
import com.example.itunesparsingapiassignment3.viewmodel.SongsViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopSongsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopSongsFragment : Fragment() {

    private val binding by lazy {
        FragmentPopSongsBinding.inflate(layoutInflater)
    }

    //setting my recycler view
    private val popsAdapter by lazy {
        PopsAdapter {}
    }


    @Inject
    lateinit var popViewModelFactory: PopViewModelFactory


//This approach will be the same as passing fragments as the store owner
//private val viewModel: SongsViewModel by viewModels()

//This approach will be the same as passing activity as the store owner (needs fragment nav implement.)
// private val viewModel: SongsViewModel by activityViewModels()

    //instantiating the viewModel
    private val popViewModel: PopViewModel by lazy {
/*provides you with the viewModel (viewModel's store owner (RockSongsFragment) needed and factory
factory object will be created for you, default factory is when you have no arguments in the ViewModel
, no arguments to be passed, but if we have arguments, we need to create our own viewModel's factory
 The owner in this case is the fragment
 */
        //Creating viewModel factory
       // ViewModelProvider(requireActivity(), popViewModelFactory)[PopViewModel::class.java]
        ViewModelProvider(requireActivity(),popViewModelFactory)[PopViewModel::class.java]
        //  model will be stored in the activity store owner, if you move between fragments, the store
        //owner will be the same
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //calling Dagger component  to inject the dependencies
       // MusicApp.musicComponent.inject(this)
        MusicApp.musicComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding swipe to refresh layout
        val swipe : SwipeRefreshLayout = binding.swipeToRefresh


        //binding the recycler view
        binding.popSongsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            //setting the adapter
            adapter = popsAdapter

        }

        popViewModel.popSongs.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.LOADING -> {
                    binding.loadingSpinner.visibility = View.VISIBLE
                    binding.popSongsRv.visibility = View.GONE
                }
                is UIState.SUCCESS -> {
                    binding.loadingSpinner.visibility = View.GONE
                    binding.popSongsRv.visibility = View.VISIBLE

                    popsAdapter.updateSongs(state.songs)
                }
                is UIState.ERROR -> {
                    binding.loadingSpinner.visibility = View.GONE
                    binding.popSongsRv.visibility = View.GONE

                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error occured")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry") { dialog, _ ->
                            popViewModel.getMusic(Genre.POP)
                            dialog.dismiss()
                        }
                        //necessary statements to load the dialog
                        .create()
                        .show()
                }
            }

        }

        popViewModel.getMusic(Genre.POP)

        //Adding refresh on swipe property
        swipe.setOnRefreshListener {
            popViewModel.getMusic(Genre.POP)
            popsAdapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
