package com.example.animelistings.ui.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    // Construct the lazy list states for the list and the details outside of deciding which one to
    // show. This allows the associated state to survive beyond that decision, and therefore
    // we get to preserve the scroll throughout any changes to the content.
    val homeListLazyListState = rememberLazyListState()

    HomeFeedScreen(
        uiState = uiState,
        onSelectListing = { homeViewModel.selectListing(it) },
        onRefreshListings = { homeViewModel.refreshListings() },
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
    )
}