package com.josemiz.flickrapp.tv.search.view


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.IconButton
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.josemiz.flickrapp.R
import com.josemiz.flickrapp.entity.PhotoColumn
import com.josemiz.flickrapp.entity.PhotoEntity
import com.josemiz.flickrapp.tv.detail.view.PhotoDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PhotoSearch(
    photoColumn: PhotoColumn?,
    onSearchPhoto: (String) -> Unit
) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    val detailVisibility = remember { mutableStateOf(false) }
    val photoDetail = remember { mutableStateOf(PhotoEntity("", "", "")) }

    BackHandler(enabled = true) {
        detailVisibility.value = false
    }

    if (detailVisibility.value) {
        photoColumn?.let { PhotoDetail(photoEntity = photoDetail.value, list = it.photos) }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(40.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier.align(CenterVertically),
                    shape = ButtonDefaults.shape(CircleShape),
                    onClick = { onSearchPhoto.invoke(inputValue.value.text) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_search_96),
                        modifier = Modifier
                            .background(color = Color.Cyan)
                            .padding(10.dp),
                        contentDescription = "Search"
                    )

                }
                BasicTextField(
                    value = inputValue.value,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .background(Color.White)
                        .padding(all = 16.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                    ),
                    onValueChange = {
                        inputValue.value = it
                        onSearchPhoto.invoke(it.text)
                    },
                    maxLines = 1,
                    singleLine = true
                )
            }
            PhotoGrid(photoColumn = photoColumn) {
                photoDetail.value = it
                detailVisibility.value = true
            }
        }
    }
}

@Composable
fun PhotoGrid(photoColumn: PhotoColumn?, onPhotoClicked: (PhotoEntity) -> Unit) {
    val state = rememberTvLazyGridState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        photoColumn?.let {
            Text(
                text = photoColumn.title,
                color = Color.White,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            TvLazyVerticalGrid(columns = TvGridCells.Fixed(3), state = state, content = {
                items(photoColumn.photos) {
                    PhotoCard(photoEntity = it) { photo ->
                        scope.launch {
                            state.animateScrollToItem(photoColumn.photos.indexOf(photo), 0)
                        }
                        onPhotoClicked.invoke(photo)
                    }
                }
            })
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PhotoCard(photoEntity: PhotoEntity, onPhotoClicked: (PhotoEntity) -> Unit) {
    Card(
        onClick = {},
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .clickable { onPhotoClicked.invoke(photoEntity) }
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoEntity.url)
                    .placeholder(R.drawable.app_icon_your_company)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.height(180.dp),
                placeholder = painterResource(R.drawable.app_icon_your_company),
                contentDescription = photoEntity.title,
                contentScale = ContentScale.Crop
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
                    maxLines = 2,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Text(
                    text = photoEntity.date,
                    color = Color.White
                )
            }
        }
    }
}