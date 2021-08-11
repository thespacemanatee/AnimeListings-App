package com.example.animelistings.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnimeDao {
    @Query("SELECT * FROM DatabaseAnime")
    fun getAllAnime(): LiveData<List<DatabaseAnime>>

    @Query("SELECT * FROM DatabaseAnime WHERE id = :id")
    fun getAnimeById(id: Int): LiveData<DatabaseAnime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAnime(vararg anime: DatabaseAnime)
}

@Database(entities = [DatabaseAnime::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract val animeDao: AnimeDao
}

private lateinit var INSTANCE: AnimeDatabase

fun getDatabase(context: Context): AnimeDatabase {
    synchronized(AnimeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, AnimeDatabase::class.java,
                "anime"
            )
                .build()
        }
        return INSTANCE
    }
}
