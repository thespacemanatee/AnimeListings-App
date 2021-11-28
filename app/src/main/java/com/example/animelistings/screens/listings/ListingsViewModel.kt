package com.example.animelistings.screens.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animelistings.repository.AnimeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListingsViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    inner class Fields : ListingsFields {
        override val refreshListingsListener: () -> Unit = {
            refreshListings()
        }
    }

    val anime = animeRepository.anime
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