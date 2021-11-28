package com.example.animelistings.screens.listing_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.domain.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingDetailsViewModel @Inject internal constructor(
    animeDatabase: AnimeDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val selectedAnime: LiveData<Anime>

    init {
        val animeId: Int? = savedStateHandle["id"]
        selectedAnime = Transformations.map(animeDatabase.animeDao().getAnimeById(animeId!!)) {
            it.asDomainModel()
        }
    }
}