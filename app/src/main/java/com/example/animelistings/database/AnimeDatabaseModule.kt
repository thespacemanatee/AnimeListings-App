package com.example.animelistings.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AnimeDatabaseModule {

    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase = Room.databaseBuilder(
        context,
        AnimeDatabase::class.java,
        "anime.db"
    ).build()
}