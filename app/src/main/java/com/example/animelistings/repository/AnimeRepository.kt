package com.example.animelistings.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.network.asDatabaseModel
import com.example.animelistings.network.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AnimeRepository(private val database: AnimeDatabase) {

    val anime = Transformations.map(database.animeDao.getAllAnime()) {
        it.asDomainModel()
    }

    suspend fun refreshAnime() {
        withContext(Dispatchers.IO) {
            try {
                val animeList = service.getTopAnimeByType(1, "airing")
                database.animeDao.insertAllAnime(*animeList.asDatabaseModel())
            } catch (e: HttpException) {
                Log.e("AnimeRepository", e.message())
            }
        }
    }
}