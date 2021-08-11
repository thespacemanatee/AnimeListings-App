package com.example.animelistings.listings

import android.app.Application
import androidx.lifecycle.*
import com.example.animelistings.database.getDatabase
import com.example.animelistings.repository.AnimeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.ClassCastException

class ListingsViewModel(application: Application) : AndroidViewModel(application) {
    private val animeRepository = AnimeRepository(getDatabase(application))
    val anime = animeRepository.anime
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    init {
        uiScope.launch {
            animeRepository.refreshAnime()
        }
    }

    fun navigateToDetailsCompleted() {
        _navigateToDetails.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListingsViewModel::class.java)) {
                return ListingsViewModel(application) as T
            } else {
                throw ClassCastException("Unknown viewModel type $modelClass")
            }
        }

    }
}