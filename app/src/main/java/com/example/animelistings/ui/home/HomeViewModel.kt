package com.example.animelistings.ui.home

import androidx.lifecycle.*
import com.example.animelistings.domain.Anime
import com.example.animelistings.repository.AnimeRepository
import com.example.animelistings.screens.listings.ListingsFields
import com.example.animelistings.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val results: List<Anime> = listOf(),
    val isLoading: Boolean = false,
    val selectedAnimeId: Int? = null,
    val isListingOpen: Boolean = false,
    val error: Error? = null
) {
    sealed class Error {
        object NetworkError : Error()
    }
}

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val animeRepository: AnimeRepository,
) : ViewModel() {

    inner class Fields : ListingsFields {
        override val refreshListingsListener: () -> Unit = {
            refreshListings()
        }
    }

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

//    val selectedAnime =
//        Transformations.map(animeDatabase.animeDao().getAnimeById(savedStateHandle["id"] ?: 0)) {
//            it.asDomainModel()
//        }
//    val isRefreshing: LiveData<Boolean>
//        get() = _isRefreshing
//
//    private val _isRefreshing = MutableLiveData(false)
//
//    private val viewModelJob = SupervisorJob()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        refreshListings()
        viewModelScope.launch {
            animeRepository.animeCollection.collect { collection ->
                _uiState.update { it.copy(results = collection) }
            }
        }
    }

    fun refreshListings() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update {
                when (animeRepository.refreshAnime()) {
                    is Resource.Success -> it.copy(
                        isLoading = false, error = null
                    )
                    is Resource.Error -> it.copy(
                        isLoading = false, error = HomeUiState.Error.NetworkError
                    )
                }
            }
        }
    }

    /**
     * Selects the given listing to view more information about it.
     */
    fun selectListing(animeId: Int) {
        // Treat selecting a detail as simply interacting with it
        interactedWithListingDetails(animeId)
    }

    /**
     * Notify that the user interacted with the listings details
     */
    private fun interactedWithListingDetails(animeId: Int) {
        _uiState.update {
            it.copy(
                selectedAnimeId = animeId,
                isListingOpen = true
            )
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
}