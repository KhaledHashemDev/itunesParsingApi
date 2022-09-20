package com.example.itunesparsingapiassignment3.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.itunesparsingapiassignment3.adapter.ClassicsAdapter
import com.example.itunesparsingapiassignment3.databinding.FragmentClassicSongsBinding
import com.example.itunesparsingapiassignment3.di.MusicApp
import com.example.itunesparsingapiassignment3.utils.ClassicViewModelFactory
import com.example.itunesparsingapiassignment3.utils.Genre
import com.example.itunesparsingapiassignment3.utils.UIState
import com.example.itunesparsingapiassignment3.viewmodel.ClassicViewModel
import javax.inject.Inject


class ClassicSongsFragment : Fragment() {

    private val binding by lazy {
        FragmentClassicSongsBinding.inflate(layoutInflater)
    }

    //setting my recycler view
    private val classicsAdapter by lazy {
        ClassicsAdapter{}
    }
    @Inject
    lateinit var classicViewModelFactory : ClassicViewModelFactory


//This approach will be the same as passing fragments as the store owner
//private val viewModel: SongsViewModel by viewModels()

//This approach will be the same as passing activity as the store owner (needs fragment nav implement.)
// private val viewModel: SongsViewModel by activityViewModels()

    //instantiating the viewModel
    private val classicViewModel: ClassicViewModel by lazy {
/*provides you with the viewModel (viewModel's store owner (RockSongsFragment) needed and factory
factory object will be created for you, default factory is when you have no arguments in the ViewModel
, no arguments to be passed, but if we have arguments, we need to create our own viewModel's factory
 The owner in this case is the fragment
 */
        //Creating viewModel factory
        //ViewModelProvider(requireActivity(), classicViewModelFactory)[ClassicViewModel::class.java]
          ViewModelProvider(requireActivity(), classicViewModelFactory)[ClassicViewModel::class.java]
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

//binding swipe to refresh layout
        val swipe : SwipeRefreshLayout = binding.swipeToRefresh

        //binding the recycler view
        binding.classicSongsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            //setting the adapter
            adapter = classicsAdapter

            setOnClickListener{
            }
        }

        classicViewModel.classicSongs.observe(viewLifecycleOwner) { state->
            when(state)  {
                is UIState.LOADING -> {
                    binding.loadingSpinner.visibility = View.VISIBLE
                    binding.classicSongsRv.visibility = View.GONE
                }
                is UIState.SUCCESS -> {
                    binding.loadingSpinner.visibility = View.GONE
                    binding.classicSongsRv.visibility = View.VISIBLE

                    classicsAdapter.updateSongs(state.songs)
                }
                is UIState.ERROR ->{
                    binding.loadingSpinner.visibility = View.GONE
                    binding.classicSongsRv.visibility = View.GONE

                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error occured")
                        .setMessage(state.error.localizedMessage)
                        .setNegativeButton("DISMISS") {dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Retry"){ dialog, _ ->
                            classicViewModel.getMusic(Genre.CLASSIC)
                            dialog.dismiss()
                        }
                        //necessary statements to load the dialog
                        .create()
                        .show()
                }
            }

        }

        classicViewModel.getMusic(Genre.CLASSIC)

        //Adding refresh on swipe property
        swipe.setOnRefreshListener {
            classicViewModel.getMusic(Genre.CLASSIC)
            classicsAdapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}
