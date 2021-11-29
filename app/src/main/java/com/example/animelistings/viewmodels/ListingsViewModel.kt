package com.example.animelistings.viewmodels

import androidx.lifecycle.*
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.database.asDomainModel
import com.example.animelistings.repository.AnimeRepository
import com.example.animelistings.screens.listings.ListingsFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingsViewModel @Inject internal constructor(
    private val animeRepository: AnimeRepository,
    animeDatabase: AnimeDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    inner class Fields : ListingsFields {
        override val refreshListingsListener: () -> Unit = {
            refreshListings()
        }
    }

    val animeCollection = animeRepository.animeCollection
    val selectedAnime =
        Transformations.map(animeDatabase.animeDao().getAnimeById(savedStateHandle["id"] ?: 0)) {
            it.asDomainModel()
        }
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _isRefreshing = MutableLiveData(false)

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        refreshListings()
    }

    private fun refreshListings() {
        _isRefreshing.value = true
        uiScope.launch {
            animeRepository.refreshAnime()
            _isRefreshing.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}