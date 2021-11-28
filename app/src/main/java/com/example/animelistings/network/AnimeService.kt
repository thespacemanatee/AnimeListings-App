package com.example.animelistings.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.jikan.moe/v3/"

interface AnimeService {
    @GET("top/anime/{page}/{subtype}")
    suspend fun getTopAnimeByType(
        @Path("page") page: Int,
        @Path("subtype") subtype: String
    ): NetworkAnimeContainer
}