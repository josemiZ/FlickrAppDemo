package com.josemiz.flickrapp.tv.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.josemiz.flickrapp.tv.search.view.PhotoSearch
import com.josemiz.flickrapp.tv.search.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvActivity : ComponentActivity() {

    private val viewModel: TvViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoSearch(viewModel = viewModel)
        }
    }
}