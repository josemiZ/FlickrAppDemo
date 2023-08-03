package com.josemiz.flickrapp.entity

data class PhotoColumn(
    val title: String,
    val photos: List<PhotoEntity>
)

data class PhotoEntity(
    val title: String,
    val date: String,
    val url: String?
)
