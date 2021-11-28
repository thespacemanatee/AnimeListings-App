package com.example.animelistings.di

import com.example.animelistings.repository.AnimeRepository
import com.example.animelistings.repository.AnimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AnimeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: AnimeRepositoryImpl): AnimeRepository
}