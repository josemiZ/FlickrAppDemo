package com.josemiz.flickrapp.repository.remote.request

import com.josemiz.flickrapp.entity.PhotoColumn

interface PhotoRequest {

    suspend fun getRecentPhotos() : PhotoColumn
    suspend fun searchPhotos(text: String) : PhotoColumn

}