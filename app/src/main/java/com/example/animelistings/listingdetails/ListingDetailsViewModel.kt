package com.example.animelistings.listingdetails

import android.app.Application
import androidx.lifecycle.*
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.database.getDatabase
import java.lang.ClassCastException

class ListingDetailsViewModel(id: Int, application: Application) : AndroidViewModel(application) {

    val selectedAnime = Transformations.map(getDatabase(application).animeDao.getAnimeById(id)) {
        it.asDomainModel()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val id: Int, private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListingDetailsViewModel::class.java)) {
                return ListingDetailsViewModel(id, application) as T
            } else {
                throw ClassCastException("Unknown viewModel type $modelClass")
            }
        }
    }
}