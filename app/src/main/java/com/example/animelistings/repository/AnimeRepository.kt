package com.example.animelistings.repository

import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.domain.Anime
import com.example.animelistings.network.AnimeService
import com.example.animelistings.network.asDatabaseModel
import com.example.animelistings.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

interface AnimeRepository {
    val animeCollection: Flow<List<Anime>>

    suspend fun refreshAnime(): Resource<Boolean>
}

class AnimeRepositoryImpl @Inject constructor(
    private val database: AnimeDatabase,
    private val service: AnimeService
) : AnimeRepository {

    override val animeCollection = database.animeDao().getAllAnime().map { it.asDomainModel() }

    override suspend fun refreshAnime(): Resource<Boolean> {
        var res: Resource<Boolean>
        withContext(Dispatchers.IO) {
            res = try {
                val animeList = service.getTopAnimeByType(1, "tv")
                Timber.d("Refreshing anime: ${animeList.anime}")
                database.animeDao().run {
                    insertAllAnime(*animeList.asDatabaseModel())
                }
                Resource.Success(true)
            } catch (e: HttpException) {
                Timber.e(e.message())
                Resource.Error(e.message())
            }
        }
        return res
    }
}