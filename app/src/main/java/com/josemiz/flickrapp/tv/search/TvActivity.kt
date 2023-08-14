package com.josemiz.flickrapp.tv.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.josemiz.flickrapp.tv.search.view.Example
import com.josemiz.flickrapp.tv.search.view.PhotoSearch
import com.josemiz.flickrapp.tv.search.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvActivity : ComponentActivity() {

    private val viewModel: TvViewModel by viewModels()

    //Detail image -> Carousel
    // search focus and keep
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val photos by viewModel.photoLiveData.observeAsState()
            PhotoSearch(photos, viewModel::searchPhotos)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRecentPhotos()
    }
}