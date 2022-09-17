package com.example.itunesparsingapiassignment3.model


import com.google.gson.annotations.SerializedName

//Songs object
data class Songs(
    @SerializedName("resultCount")
    val resultCount: Int? = null,
    @SerializedName("results")
    val songs: List<Song?>? = null
)