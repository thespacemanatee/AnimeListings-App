package com.example.animelistings.screens.listing_details

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel

class ListingDetailsViewModel(id: Int, animeDatabase: AnimeDatabase) : ViewModel() {

    val selectedAnime = Transformations.map(animeDatabase.animeDao().getAnimeById(id)) {
        it.asDomainModel()
    }
}