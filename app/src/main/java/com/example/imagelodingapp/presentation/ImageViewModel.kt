package com.example.imagelodingapp.presentation

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagelodingapp.data.UnsplashRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject



class ImageViewModel @Inject constructor(private val repository: UnsplashRepository) : ViewModel() {
    private val _images = MutableLiveData<List<Bitmap>>()
    val images: LiveData<List<Bitmap>> = _images

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 1

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (_isLoading.value == true) return
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fetchedImages = repository.getRandomPhotos(count = currentPage)
                _images.postValue(_images.value.orEmpty() + fetchedImages)
                currentPage++
            } catch (e: Exception) {
                Log.e("ImageViewModel", "Error fetching images: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}



