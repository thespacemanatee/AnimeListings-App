package com.example.animelistings.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animelistings.domain.Anime

@Entity
data class DatabaseAnime(
    val id: Int,
    @PrimaryKey val rank: Int,
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

fun DatabaseAnime.asDomainModel() = Anime(
    id, rank, title, url, imageUrl, type, episodes, startDate, endDate, members, score
)

fun List<DatabaseAnime>.asDomainModel() = map {
    Anime(
        it.id, it.rank, it.title, it.url, it.imageUrl, it.type, it.episodes, it.startDate,
        it.endDate, it.members, it.score
    )
}
