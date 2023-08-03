package com.josemiz.flickrapp.repository.remote.interfaces

import com.josemiz.flickrapp.repository.remote.data.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosConnection {
    @GET(".")
    suspend fun getRecent(
        @Query("api_key") apiKey: String,
        @Query("method") method: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") callback: Int = 1,
        @Query("extras") extras: String = "date_upload,url_o"
    ): Data

    @GET(".")
    suspend fun searchPhoto(
        @Query("api_key") apiKey: String,
        @Query("method") method: String,
        @Query("text") text: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") callback: Int = 1,
        @Query("extras") extras: String = "date_upload,url_o"
    ): Data

}