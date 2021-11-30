package com.example.animelistings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animelistings.ui.home.HomeRoute
import com.example.animelistings.ui.home.HomeViewModel

@Composable
fun AnimeListingsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = AnimeListingsDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AnimeListingsDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeRoute(homeViewModel, openDrawer)
        }
//        composable(AnimeListingsDestinations.LISTINGS_DETAILS_ROUTE) {
//            val interestsViewModel: InterestsViewModel = viewModel(
//                factory = InterestsViewModel.provideFactory(appContainer.interestsRepository)
//            )
//            InterestsRoute(
//                interestsViewModel = interestsViewModel,
//            )
//        }
    }
}
