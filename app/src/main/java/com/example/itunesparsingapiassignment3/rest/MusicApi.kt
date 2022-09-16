package com.example.itunesparsingapiassignment3.rest

import com.example.itunesparsingapiassignment3.model.Songs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface Api will be responsible for making the network call
 */
interface MusicApi {


    /**
     * Here we are calling this function from a coroutine scope (suspend)
     * This method is calling this
     */
    @GET(PATH_SEARCH)
    suspend fun getMusic(
        @Query("term") genre: String? = null, //pop, rock, classic
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50,
    ): Response<Songs>



    companion object {

        //search is  the database, term is the query that contains either (rock, classic, pop)
        const val BASE_URL =
            "https://itunes.apple.com/"
        private  const val PATH_SEARCH = "search"

        private val okhttp =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY

                    }
                )
                .build()


        val serviceApi: MusicApi =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()
                .create(MusicApi::class.java)
    }
}