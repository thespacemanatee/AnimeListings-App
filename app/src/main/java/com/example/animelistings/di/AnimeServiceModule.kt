package com.example.animelistings.di

import com.example.animelistings.network.AnimeService
import com.example.animelistings.network.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AnimeServiceModule {

    @Provides
    @Singleton
    fun provideService(): AnimeService {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        return retrofit.create(AnimeService::class.java)
    }
}