package com.example.animelistings.network

import com.example.animelistings.database.DatabaseAnime
import com.example.animelistings.domain.Anime
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkAnimeContainer(@Json(name = "top") val anime: List<NetworkAnime>)

@JsonClass(generateAdapter = true)
data class NetworkAnime(
    @Json(name = "mal_id") val id: Int,
    val rank: Int,
    val title: String,
    val url: String,
    @Json(name = "image_url") val imageUrl: String,
    val type: String,
    val episodes: Int?,
    @Json(name = "start_date") val startDate: String,
    @Json(name = "end_date") val endDate: String?,
    val members: Int,
    val score: Double
)

fun NetworkAnimeContainer.asDomainModel() = anime.map {
    Anime(
        it.id, it.rank, it.title, it.url, it.imageUrl, it.type, it.episodes ?: 0,
        it.startDate, it.endDate ?: "", it.members, it.score
    )
}

fun NetworkAnimeContainer.asDatabaseModel() = anime.map {
    DatabaseAnime(
        it.id, it.rank, it.title, it.url, it.imageUrl, it.type, it.episodes ?: 0,
        it.startDate, it.endDate ?: "", it.members, it.score
    )
}.toTypedArray()
