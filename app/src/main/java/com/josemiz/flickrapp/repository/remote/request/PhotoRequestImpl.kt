package com.josemiz.flickrapp.repository.remote.request

import com.josemiz.flickrapp.entity.PhotoColumn
import com.josemiz.flickrapp.entity.PhotoEntity
import com.josemiz.flickrapp.repository.remote.interfaces.PhotosConnection
import com.josemiz.flickrapp.util.convertLongToTime
import javax.inject.Inject

class PhotoRequestImpl @Inject constructor(
    private val photosConnection : PhotosConnection
) : PhotoRequest {
    override suspend fun getRecentPhotos(): PhotoColumn {
        try {
            val photos = photosConnection.getRecent("1d8fe3410376dfe00d400f4ce2c236d2","flickr.photos.getRecent")
            return PhotoColumn("Trending Now", photos.photo?.list?.map {
                PhotoEntity(it.title,it.date.convertLongToTime(),it.url)
            }?: emptyList())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun searchPhotos(text: String): PhotoColumn {
        try {
            val photos = photosConnection.searchPhoto("1d8fe3410376dfe00d400f4ce2c236d2","flickr.photos.search", text)
            return PhotoColumn("Trending Now", photos.photo?.list?.map {
                PhotoEntity(it.title,it.date.convertLongToTime(),it.url)
            }?: emptyList())
        } catch (e: Exception) {
            throw e
        }
    }
}