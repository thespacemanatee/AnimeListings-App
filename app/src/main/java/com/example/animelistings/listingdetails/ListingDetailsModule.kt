package com.example.animelistings.listingdetails

import androidx.fragment.app.Fragment
import com.example.animelistings.database.AnimeDatabase
import com.example.animelistings.util.viewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object ListingDetailsModule {

    @Provides
    fun provideViewModel(
        fragment: Fragment,
        animeDatabase: AnimeDatabase
    ): ListingDetailsViewModel {
        val id = ListingDetailsFragmentArgs.fromBundle(fragment.requireArguments()).id
        return fragment.viewModel {
            ListingDetailsViewModel(id, animeDatabase)
        }
    }
}