package com.example.animelistings.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM DatabaseAnime")
    fun getAllAnime(): Flow<List<DatabaseAnime>>

    @Query("SELECT * FROM DatabaseAnime WHERE id = :id")
    fun getAnimeById(id: Int): Flow<DatabaseAnime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAnime(vararg anime: DatabaseAnime)
}

@Database(entities = [DatabaseAnime::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}