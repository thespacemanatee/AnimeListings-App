package com.example.animelistings.ui.listing_details

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.animelistings.R
import com.example.animelistings.domain.Anime
import com.example.animelistings.ui.listing_details.ListingDetailsConstants.AppBarCollapsedHeight
import com.example.animelistings.ui.listing_details.ListingDetailsConstants.AppBarExpandedHeight
import com.example.animelistings.ui.listing_details.ListingDetailsConstants.AppBarHorizontalPadding
import com.example.animelistings.ui.theme.AnimeListingsTheme
import com.example.animelistings.ui.utils.ShareButton
import com.google.accompanist.insets.navigationBarsPadding
import kotlin.math.max
import kotlin.math.min


/**
 * Stateless Article Screen that displays a single post adapting the UI to different screen sizes.
 *
 * @param anime (state) item to display
 * @param onBack (event) request navigate back
 * @param lazyListState (state) the [LazyListState] for the article content
 */
@Composable
fun ListingDetailsScreen(
    anime: Anime,
    onBack: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val context = LocalContext.current
    ListingDetailsScreenContent(
        anime = anime,
        // Allow opening the Drawer if the screen is not expanded
        navigationIconContent = {
            BackNavigationButton(
                onBack,
                modifier = Modifier
                    .height(AppBarCollapsedHeight)
                    .padding(horizontal = AppBarHorizontalPadding)
            )
        },
        // Show the bottom bar if the screen is not expanded
        bottomBarContent = {
            BottomBar(
                onShareAnime = { sharePost(anime, context) },
                modifier = Modifier.navigationBarsPadding(start = false, end = false)
            )
        },
        state = lazyListState
    )
}

/**
 * Stateless Listing Details Screen that displays a single anime.
 *
 * @param anime (state) item to display
 * @param navigationIconContent (UI) content to show for the navigation icon
 * @param bottomBarContent (UI) content to show for the bottom bar
 */
@Composable
private fun ListingDetailsScreenContent(
    anime: Anime,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    state: LazyListState = rememberLazyListState()
) {
    ListingContent(
        anime = anime,
        state = state
    )
    ListingDetailsCollapsingToolbar(
        anime = anime,
        state = state,
        navigationIconContent = navigationIconContent
    )
}

@Composable
private fun ListingDetailsCollapsingToolbar(
    anime: Anime,
    state: LazyListState,
    navigationIconContent: @Composable (() -> Unit)? = null,
) {
    val imageHeight = AppBarExpandedHeight - AppBarCollapsedHeight
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = min(state.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - maxOffset * 2f) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        elevation = if (offset == maxOffset) 4.dp else 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .height(AppBarExpandedHeight)
            .offset { IntOffset(x = 0, y = -offset) },
    ) {
        Column {
            ListingHeaderImage(anime, imageHeight, offsetProgress)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = anime.title,
                    fontSize = (25 - 7 * offsetProgress).sp,
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = (16 + 48 * offsetProgress).dp)
                )
            }
        }
    }
    if (navigationIconContent != null) {
        navigationIconContent()
    }
}

@Composable
private fun ListingHeaderImage(
    anime: Anime,
    imageHeight: Dp,
    offsetProgress: Float
) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(imageHeight)
        .alpha(1f - offsetProgress)
    Image(
        painter = rememberImagePainter(data = anime.imageUrl),
        contentDescription = null, // decorative
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun BackNavigationButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        IconButton(
            onClick = onBack,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.cd_navigate_up),
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

/**
 * Bottom bar for Article screen
 *
 * @param onShareAnime (event) request this post to be shared
 */
@Composable
private fun BottomBar(
    onShareAnime: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            ShareButton(onClick = onShareAnime)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * Show a share sheet for a post
 *
 * @param anime to share
 * @param context Android context to show the share sheet in
 */
fun sharePost(anime: Anime, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, anime.title)
        putExtra(Intent.EXTRA_TEXT, anime.url)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.listing_details_share_anime)
        )
    )
}

@Preview("Listing details screen")
@Preview("Listing details screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeListDrawerScreen() {
    AnimeListingsTheme {
        ListingDetailsScreen(
            anime = Anime(
                1, 1, "Shingeki no Kyojin: The Final Season Part 2",
                "https://myanimelist.net/anime/48583/Shingeki_no_Kyojin__The_Final_Season_Part_2",
                "https://cdn.myanimelist.net/images/anime/1988/119437.jpg?s=aad31fb4d3d6d893c32a52ae666698ac",
                "TV", 0, "Jan 2022", "", 375548, 0.0
            ),
            onBack = { },
        )
    }
}
