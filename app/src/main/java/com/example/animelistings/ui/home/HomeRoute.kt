package com.example.animelistings.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import com.example.animelistings.ui.home.HomeScreenType.Feed
import com.example.animelistings.ui.home.HomeScreenType.ListingDetails
import com.example.animelistings.ui.listing_details.ListingDetailsScreen

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    // Construct the lazy list states for the list and the details outside of deciding which one to
    // show. This allows the associated state to survive beyond that decision, and therefore
    // we get to preserve the scroll throughout any changes to the content.
    val homeListLazyListState = rememberLazyListState()
    val listingDetailLazyListStates = uiState.results.associate { anime ->
        key(anime.id) {
            anime.id to rememberLazyListState()
        }
    }

    when (getHomeScreenType(uiState)) {
        Feed -> {
            HomeFeedScreen(
                uiState = uiState,
                onSelectListing = { homeViewModel.selectListing(it) },
                onRefreshListings = { homeViewModel.refreshListings() },
                onErrorDismiss = { homeViewModel.errorShown() },
                openDrawer = openDrawer,
                homeListLazyListState = homeListLazyListState,
                scaffoldState = scaffoldState,
            )
        }
        ListingDetails -> {
            uiState.selectedAnime?.let {
                ListingDetailsScreen(
                    anime = it,
                    onBack = { homeViewModel.interactedWithHome() },
                    lazyListState = listingDetailLazyListStates.getValue(
                        uiState.selectedAnime!!.id
                    )
                )
            }

            // If we are just showing the detail, have a back press switch to the home.
            // This doesn't take anything more than notifying that we "interacted with the home page"
            // since that is what drives the display of the home page
            BackHandler {
                homeViewModel.interactedWithHome()
            }
        }

    }
}

/**
 * A precise enumeration of which type of screen to display at the home route.
 *
 * There are 2 options:
 * - [Feed], which displays just the list of all articles
 * - [ListingDetails], which displays just a specific article.
 */
private enum class HomeScreenType {
    Feed,
    ListingDetails
}

/**
 * Returns the current [HomeScreenType] to display, based on whether or not the screen is expanded
 * and the [HomeUiState].
 */
@Composable
private fun getHomeScreenType(
    uiState: HomeUiState
): HomeScreenType =
    if (uiState.isListingOpen) ListingDetails else Feed
