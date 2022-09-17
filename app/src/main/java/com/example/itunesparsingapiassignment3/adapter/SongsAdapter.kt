package com.example.itunesparsingapiassignment3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesparsingapiassignment3.databinding.SongItemBinding
import com.example.itunesparsingapiassignment3.model.Song


class SongsAdapter(private val songsData: MutableList<Song> = mutableListOf()
) : RecyclerView.Adapter<SongsViewHolder>() {


    //instead of creating many instances of the adapter
    fun updateSongs(newSongs: List<Song>){
           songsData.clear()
           songsData.addAll(newSongs)
           notifyDataSetChanged() //notifying the recycler view

    }

    //Getting an instance of the viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder =
SongsViewHolder(
    SongItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
)


    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) =
        holder.bind(songsData[position])


    override fun getItemCount(): Int = songsData.size

}

//fun bind goes into the ViewHolder
class SongsViewHolder( private val binding: SongItemBinding
) : RecyclerView.ViewHolder(binding.root) {

fun bind(song : Song){
    binding.collectionName.text = song.collectionName
    binding.artistName.text = song.artistName
    binding.trackPrice.text = song.trackPrice.toString()
  }
}