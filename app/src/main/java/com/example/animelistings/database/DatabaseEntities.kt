package com.example.animelistings.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animelistings.domain.Anime
import com.squareup.moshi.Json

@Entity
data class DatabaseAnime(
    @PrimaryKey val id: Int,
    val rank: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val type: String,
    val episodes: Int,
    val startDate: String,
    val endDate: String,
    val members: Int,
    val score: Double
)

fun DatabaseAnime.asDomainModel(): Anime {
    return Anime(
        id,
        rank,
        title,
        url,
        imageUrl,
        type,
        episodes,
        startDate,
        endDate,
        members,
        score
    )
}

fun List<DatabaseAnime>.asDomainModel(): List<Anime> {
    return map {
        Anime(
            it.id,
            it.rank,
            it.title,
            it.url,
            it.imageUrl,
            it.type,
            it.episodes,
            it.startDate,
            it.endDate,
            it.members,
            it.score
        )
    }
}
