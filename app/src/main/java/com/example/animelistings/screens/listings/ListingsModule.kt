package com.example.animelistings.screens.listings

import androidx.fragment.app.Fragment
import com.example.animelistings.repository.AnimeRepository
import com.example.animelistings.util.viewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object ListingsModule {

    @Provides
    fun provideViewModel(fragment: Fragment, animeRepository: AnimeRepository): ListingsViewModel {
        return fragment.viewModel {
            ListingsViewModel(animeRepository)
        }
    }
}