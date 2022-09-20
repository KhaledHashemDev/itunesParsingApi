package com.example.itunesparsingapiassignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.itunesparsingapiassignment3.databinding.ActivityMainBinding
import com.example.itunesparsingapiassignment3.di.MusicApp

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MusicApp.musicComponent.inject(this)

        val navController = findNavController(R.id.frag_container_songs)
        binding.songsNav.setupWithNavController(navController)



    }
}