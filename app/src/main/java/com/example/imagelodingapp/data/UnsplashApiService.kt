package com.example.imagelodingapp.data

import com.example.imagelodingapp.domain.UnsplashPhoto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface UnsplashApiService {
    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("count") count: Int,
        @Query("client_id") clientId: String
    ): List<UnsplashPhoto>


    @GET("collections")
    suspend fun getPhotos(
        @Query("page") count: Int=5,
        @Query("per_page") per_page: Int=10
      //  @Query("client_id") clientId: String
    ): List<UnsplashPhoto>

}