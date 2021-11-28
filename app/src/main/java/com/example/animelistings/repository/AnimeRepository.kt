package com.example.animelistings.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.domain.Anime
import com.example.animelistings.network.AnimeService
import com.example.animelistings.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

interface AnimeRepository {
    val anime: LiveData<List<Anime>>

    suspend fun refreshAnime()
}

class AnimeRepositoryImpl @Inject constructor(
    private val database: AnimeDatabase,
    private val service: AnimeService
) : AnimeRepository {

    override val anime = Transformations.map(database.animeDao().getAllAnime()) {
        it.asDomainModel()
    }

    override suspend fun refreshAnime() {
        withContext(Dispatchers.IO) {
            try {
                val animeList = service.getTopAnimeByType(1, "airing")
                database.animeDao().insertAllAnime(*animeList.asDatabaseModel())
            } catch (e: HttpException) {
                Log.e("AnimeRepository", e.message())
            }
        }
    }
}