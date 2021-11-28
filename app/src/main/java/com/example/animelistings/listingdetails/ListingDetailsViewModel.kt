package com.example.animelistings.listingdetails

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import java.lang.ClassCastException

class ListingDetailsViewModel(id: Int, animeDatabase: AnimeDatabase) : ViewModel() {

    val selectedAnime = Transformations.map(animeDatabase.animeDao().getAnimeById(id)) {
        it.asDomainModel()
    }
}