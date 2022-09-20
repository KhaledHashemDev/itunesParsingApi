package com.example.itunesparsingapiassignment3.adapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesparsingapiassignment3.R
import com.example.itunesparsingapiassignment3.databinding.SongItemBinding
import com.example.itunesparsingapiassignment3.model.Songs
import com.example.itunesparsingapiassignment3.model.domain.DomainSong
import com.example.itunesparsingapiassignment3.utils.SongsViewModelFactory
import com.squareup.picasso.Picasso


class SongsAdapter(private val songsData: MutableList<DomainSong> = mutableListOf(),
                   private var onItemClick: (DomainSong) -> Unit

) : RecyclerView.Adapter<SongsViewHolder>() {


    //instead of creating many instances of the adapter
    fun updateSongs(newSongs: List<DomainSong>){
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

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(songsData[position], onItemClick)
    }

    override fun getItemCount(): Int = songsData.size

}

//fun bind goes into the ViewHolder
class SongsViewHolder( private val binding: SongItemBinding
) : RecyclerView.ViewHolder(binding.root) {

fun bind(song : DomainSong, onItemClick: (DomainSong) -> Unit
){

    //on clicking on recycler view item, implicitly play music using the url
    itemView.setOnClickListener{
        onItemClick(song)
       val url : Uri = Uri.parse(song.previewUrl)
        val i = Intent(Intent.ACTION_VIEW,url)
        itemView.context.startActivity(i)
    }

    //binding textView to data
    binding.collectionName.text = song.collectionName
    binding.artistName.text = song.artistName
    binding.trackPrice.text = song.trackPrice.toString()

    //loading image using Picasso
    Picasso.get()
        .load(song.artworkUrlImage)
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_error_24)
        .resize(300,300)
        .into(binding.artworkUrlImage)

    }
}

