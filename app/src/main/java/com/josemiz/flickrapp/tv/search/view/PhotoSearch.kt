package com.josemiz.flickrapp.tv.search.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.IconButton
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.josemiz.flickrapp.R
import com.josemiz.flickrapp.entity.PhotoColumn
import com.josemiz.flickrapp.entity.PhotoEntity
import com.josemiz.flickrapp.tv.search.viewmodel.TvViewModel

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearch(
    viewModel: TvViewModel
) {
    val photos = viewModel.photoLiveData.observeAsState()
    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadRecentPhotos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier.align(CenterVertically),
                shape = ButtonDefaults.shape(CircleShape),
                onClick = { viewModel.searchPhotos(inputValue.value.text) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_96),
                    modifier = Modifier
                        .background(color = Color.Cyan)
                        .padding(10.dp),
                    contentDescription = "Search"
                )

            }
            TextField(value = inputValue.value,
                placeholder = { Text(text = "Search") },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                ),
                onValueChange = {
                    inputValue.value = it
                    viewModel.searchPhotos(it.text)
                })
        }
        PhotoGrid(
            photoColumn = photos.value
        )
    }
}

@Composable
fun PhotoGrid(photoColumn: PhotoColumn?) {
    Column(modifier = Modifier.fillMaxSize()) {
        photoColumn?.let {
            Text(
                text = photoColumn.title,
                color = Color.White,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            TvLazyVerticalGrid(columns = TvGridCells.Fixed(3), content = {
                items(photoColumn.photos) {
                    PhotoCard(photoEntity = it)
                }
            })
        } ?: CircularProgressIndicator()
    }
}

@Composable
fun PhotoCard(photoEntity: PhotoEntity) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoEntity.url)
                .placeholder(R.drawable.app_icon_your_company)
                .crossfade(true)
                .build(),
            modifier = Modifier.height(180.dp),
            placeholder = painterResource(R.drawable.app_icon_your_company),
            contentDescription = photoEntity.title,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.6f))
                .padding(5.dp)
        ) {
            Text(
                text = photoEntity.title,
                color = Color.White,
                modifier = Modifier.padding(vertical = 5.dp)
            )
            Text(
                text = photoEntity.date,
                color = Color.White
            )
        }
    }
}