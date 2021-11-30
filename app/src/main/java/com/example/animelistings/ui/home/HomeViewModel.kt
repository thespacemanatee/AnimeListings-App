package com.example.animelistings.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelistings.domain.Anime
import com.example.animelistings.repository.AnimeRepository
import com.example.animelistings.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val results: List<Anime> = listOf(),
    val isLoading: Boolean = false,
    val selectedAnime: Anime? = null,
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

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    init {
        viewModelScope.launch {
            animeRepository.animeCollection.collect { collection ->
                _uiState.update { it.copy(results = collection, isLoading = false) }
            }
        }
        refreshListings()
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
     * Notify that an error was displayed on the screen
     */
    fun errorShown() {
        _uiState.update {
            it.copy(error = null)
        }
    }

    /**
     * Notify that the user interacted with the home page
     */
    fun interactedWithHome() {
        _uiState.update {
            it.copy(isListingOpen = false)
        }
    }

    /**
     * Notify that the user interacted with the listings details
     */
    private fun interactedWithListingDetails(animeId: Int) {
        _uiState.update { it ->
            it.copy(
                selectedAnime = it.results.find { it.id == animeId },
                isListingOpen = true
            )
        }
    }
}
