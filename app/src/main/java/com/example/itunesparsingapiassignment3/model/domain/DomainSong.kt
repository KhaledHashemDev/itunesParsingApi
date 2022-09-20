package com.example.itunesparsingapiassignment3.model.domain

import com.example.itunesparsingapiassignment3.model.Song

/**
 * This 'domain' file contains/extracts the data i actually need
 * from the list of data parsed. We ignore the rest of data since
 * we don't need it in our view.
 */

//data class
data class DomainSong(
    val collectionName : String,
    val artistName : String,
    val trackPrice: Double,
    val artworkUrlImage: String,
    val previewUrl: String
)


//extension function
fun List<Song?>?.mapToDomainSongs(): List<DomainSong> {
      return this?.map { song ->
          DomainSong(
              collectionName = song?.collectionName ?: "Invalid Collection Name",
              artistName = song?.artistName  ?: "Invalid Artist Name",
              trackPrice = song?.trackPrice ?: 0.00,
              artworkUrlImage = song?.artworkUrl60 ?: "Invalid Image",
              previewUrl = song?.previewUrl ?: "Invalid Link"
          )

//If null return an empty list
       } ?:   emptyList()

}

