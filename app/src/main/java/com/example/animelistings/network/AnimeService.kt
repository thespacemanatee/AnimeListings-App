package com.example.animelistings.network

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
