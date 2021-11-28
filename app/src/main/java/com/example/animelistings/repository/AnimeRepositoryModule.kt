package com.example.animelistings.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AnimeRepositoryModule {

    @Binds
    abstract fun bindRepository(repositoryImpl: AnimeRepositoryImpl): AnimeRepository
}