package com.example.imagelodingapp.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.imagelodingapp.R
import com.example.imagelodingapp.data.UnsplashRepository
import com.example.imagelodingapp.ui.theme.ImageLodingAppTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: UnsplashRepository

    private val viewModel: ImageViewModel by viewModels {
        ImageViewModelFactory(repository)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageLodingAppTheme {
                val images by viewModel.images.observeAsState(initial = emptyList())
                  Log.d("Response", "onCreate: "+images.size)
                val isLoading by viewModel.isLoading.observeAsState(false)
                ImageGrid(
                    images = images,
                    isLoading = isLoading,
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }
        }
    }


}


@Composable
fun ImageGrid(images: List<Bitmap>, isLoading: Boolean, onLoadMore: () -> Unit) {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(2)
    ) {
        items(images.size) { index ->
            val bitmap = images[index]

            // Check if the bitmap is not null and not in the loading state
            if (bitmap != null && !isLoading) {
                Image(
                    painter = rememberImagePainter(bitmap),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(1f)
                        .size(300.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Show a placeholder image or loader until the actual image is loaded
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(1f)
                        .size(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    }
                }
                }

            // Load more when reaching the end of the list
            if (index == images.size - 1) {
                onLoadMore()
            }
        }
    }
}


@Preview
@Composable
fun ImageGridPreview() {
    val bitmap1 = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.ic_launcher_foreground
    )

    val images = listOf(bitmap1, bitmap1, bitmap1, bitmap1)
    ImageGrid(images = images, isLoading = true) {

    }
}


