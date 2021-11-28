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
import timber.log.Timber
import javax.inject.Inject

interface AnimeRepository {
    val anime: LiveData<List<Anime>>

    suspend fun refreshAnime(): Boolean
}

class AnimeRepositoryImpl @Inject constructor(
    private val database: AnimeDatabase,
    private val service: AnimeService
) : AnimeRepository {

    override val anime = Transformations.map(database.animeDao().getAllAnime()) {
        it.asDomainModel()
    }

    override suspend fun refreshAnime(): Boolean {
        withContext(Dispatchers.IO) {
            try {
                val animeList = service.getTopAnimeByType(1, "upcoming")
                Timber.d("Refreshing anime...")
                database.animeDao().insertAllAnime(*animeList.asDatabaseModel())
            } catch (e: HttpException) {
                Timber.e(e.message())
            }
        }
        return true
    }
}