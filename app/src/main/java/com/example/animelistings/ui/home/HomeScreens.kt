package com.example.animelistings.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.animelistings.R
import com.example.animelistings.domain.Anime
import com.example.animelistings.ui.theme.AnimeListingsTheme
import com.example.animelistings.utils.isScrolled
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * The home screen displaying just the article feed.
 */
@Composable
fun HomeFeedScreen(
    uiState: HomeUiState,
    onSelectListing: (Int) -> Unit,
    onRefreshListings: () -> Unit,
    onErrorDismiss: () -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        onRefreshListings = onRefreshListings,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { contentModifier ->
        AnimeList(
            results = uiState.results,
            onListingTapped = onSelectListing,
            modifier = contentModifier,
            state = homeListLazyListState,
        )
    }
}

/**
 * A display of the home screen that has the list.
 *
 * This sets up the scaffold with the top app bar, and surrounds the [hasListingsContent] with refresh,
 * loading and error handling.
 *
 * This helper functions exists because [HomeFeedWithArticleDetailsScreen] and [HomeFeedScreen] are
 * extremely similar, except for the rendered content when there are posts to display.
 */
@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
    onRefreshListings: () -> Unit,
    onErrorDismiss: () -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    hasListingsContent: @Composable (
        modifier: Modifier
    ) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
                elevation = if (!homeListLazyListState.isScrolled) 0.dp else 4.dp
            )
        },
        modifier = modifier
    ) {
        val contentModifier = Modifier
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()

        LoadingContent(
            empty = uiState.results.isEmpty() && uiState.isLoading,
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            onRefresh = onRefreshListings,
            content = {
                if (uiState.results.isNotEmpty()) {
                    hasListingsContent(contentModifier)
                } else {
                    if (uiState.error == null) {
                        // if there are no listings, and no error, let the user refresh manually
                        TextButton(
                            onClick = onRefreshListings,
                            modifier.fillMaxSize()
                        ) {
                            Text(
                                stringResource(id = R.string.home_tap_to_load_content),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        // there's currently an error showing, don't show any content
                        Box(contentModifier.fillMaxSize()) { /* empty screen */ }
                    }
                }
            }
        )
    }

    // Process one error message at a time and show them as Snackbars in the UI
    if (uiState.error != null) {
        // Remember the errorMessage to display on the screen
        val errorMessage = remember(uiState) {
            when (uiState.error) {
                HomeUiState.Error.NetworkError -> "Network error"
            }
        }

        // Get the text to show on the message from resources
        val errorMessageText: String = errorMessage
        val retryMessageText = stringResource(id = R.string.retry)

        // If onRefreshListings or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onRefreshListingsState by rememberUpdatedState(onRefreshListings)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        // Effect running in a coroutine that displays the Snackbar on the screen
        // If there's a change to errorMessageText, retryMessageText or scaffoldState,
        // the previous effect will be cancelled and a new one will start with the new values
        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshListingsState()
            }
            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState()
        }
    }
}

/**
 * Display a feed of listings.
 *
 * When a post is clicked on, [onListingTapped] will be called.
 *
 * @param results (state) the feed to display
 * @param onListingTapped (event) request navigation to ListingDetails screen
 * @param modifier modifier for the root element
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimeList(
    results: List<Anime>,
    onListingTapped: (animeId: Int) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(results) { AnimeListing(it, onListingTapped) }
    }
}

@Composable
fun AnimeListing(
    anime: Anime,
    navigateToListingDetails: (Int) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.clickable(onClick = { navigateToListingDetails(anime.id) })
        ) {
            BannerImage(anime)
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
            ) {
                Text(anime.title)
            }
        }
    }
}


@Composable
fun BannerImage(anime: Anime) {
    Image(
        painter = rememberImagePainter(data = anime.imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
    )
}

/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param loading (state) when true, display a loading spinner over [content]
 * @param onRefresh (event) event to request refresh
 * @param content (slot) the main content to show
 */
@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

/**
 * Full screen circular progress indicator
 */
@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

/**
 * TopAppBar for the Home screen
 */
@Composable
private fun HomeTopAppBar(
    elevation: Dp,
    openDrawer: () -> Unit
) {
    val title = stringResource(id = R.string.app_name)
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.home_title)) },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    painter = painterResource(R.drawable.ic_drawer),
                    contentDescription = stringResource(R.string.cd_open_navigation_drawer),
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        actions = {
//            IconButton(onClick = { /* TODO: Open search */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = stringResource(R.string.cd_search)
//                )
//            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}


@Preview("Home list drawer screen")
@Preview("Home list drawer screen (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeListDrawerScreen() {
//    val postsFeed = runBlocking {
//        (BlockingFakePostsRepository().getPostsFeed() as Resource.Success<*>).data
//    }
    AnimeListingsTheme {
        HomeFeedScreen(
            uiState = HomeUiState(),
            onSelectListing = {},
            onRefreshListings = {},
            onErrorDismiss = {},
            openDrawer = {},
            homeListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState(),
        )
    }
}
