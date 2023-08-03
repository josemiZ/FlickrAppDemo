package com.josemiz.flickrapp.repository.remote.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("photos") val photo : PhotoList?
)

data class PhotoList(
    @SerializedName("photo") val list : List<Photo>
)

data class Photo(
    val id: String,
    val title: String,
    @SerializedName("dateupload") val date : Long,
    @SerializedName("url_o") val url : String
)