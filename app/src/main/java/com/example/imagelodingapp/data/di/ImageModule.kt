package com.example.imagelodingapp.data.di

import android.content.Context
import com.example.imagelodingapp.data.RetrofitClient
import com.example.imagelodingapp.data.UnsplashApiService
import com.example.imagelodingapp.data.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ImageModule{


    @Singleton
    @Provides
    fun provideUnsplashApiService() : UnsplashApiService {
     return RetrofitClient.createRetrofitService()
    }


    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext context: Context, unsplashApiService: UnsplashApiService) :UnsplashRepository{
        return UnsplashRepository(context,unsplashApiService)
    }
}