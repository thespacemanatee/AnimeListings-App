package com.example.animelistings.domain

data class Anime(
    val id: Int,
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
