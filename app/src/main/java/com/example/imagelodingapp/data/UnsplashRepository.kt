package com.example.imagelodingapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import com.example.imagelodingapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class UnsplashRepository @Inject constructor(
    private val context: Context,
    private val apiService: UnsplashApiService
) {
    private val memoryCache = LruCache<String, Bitmap>(Int.MAX_VALUE)



    suspend fun getRandomPhotos(count: Int): List<Bitmap> {
        val clientId = "SWbYkq20FZVgm_IElInh_BLL2tGhJvoZJVoDz7GaH3E"
        val photos = apiService.getRandomPhotos(count, clientId)
      //  val photos = apiService.getPhotos(count,10)
        return photos.map { photo ->
            val bitmap = loadImage(photo.urls.regular)
            memoryCache.put(photo.id, bitmap)
            bitmap
        }
    }



    private suspend fun loadImage(url: String): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                // Handle error loading image
                BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
            }
        }
    }
}
