package com.example.imagelodingapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitClient {

     fun createRetrofitService(): UnsplashApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UnsplashApiService::class.java)
    }

}