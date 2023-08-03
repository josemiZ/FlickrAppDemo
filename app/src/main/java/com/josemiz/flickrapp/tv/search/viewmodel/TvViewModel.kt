package com.josemiz.flickrapp.tv.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josemiz.flickrapp.entity.PhotoColumn
import com.josemiz.flickrapp.repository.remote.request.PhotoRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val photoRequest: PhotoRequest
) : ViewModel() {

    val photoLiveData = MutableLiveData<PhotoColumn>()

    fun loadRecentPhotos() {
        viewModelScope.launch {
            photoLiveData.value = photoRequest.getRecentPhotos()
        }
    }

    fun searchPhotos(text: String) {
        viewModelScope.launch {
            photoLiveData.value = photoRequest.searchPhotos(text)
        }
    }

}