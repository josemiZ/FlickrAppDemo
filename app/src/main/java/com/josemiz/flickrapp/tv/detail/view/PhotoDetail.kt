package com.josemiz.flickrapp.tv.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.josemiz.flickrapp.R
import com.josemiz.flickrapp.entity.PhotoEntity

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PhotoDetail(photoEntity: PhotoEntity, list: List<PhotoEntity>) {
    val carouselState = remember { CarouselState(list.indexOf(photoEntity)) }
    Carousel(
        itemCount = list.size,
        carouselState = carouselState,
        modifier = Modifier.fillMaxSize()
    ) { index ->
        CarouselItem(list[index])
    }
}

@Composable
fun CarouselItem(photoEntity: PhotoEntity) {
    Column(Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoEntity.url)
                .placeholder(R.drawable.app_icon_your_company)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(R.drawable.app_icon_your_company),
            contentDescription = photoEntity.title,
            contentScale = ContentScale.Crop
        )
    }
}

