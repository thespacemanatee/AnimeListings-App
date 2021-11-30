package com.example.animelistings.ui.listing_details

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.animelistings.R
import com.example.animelistings.domain.Anime
import com.example.animelistings.ui.utils.ShareButton
import com.example.animelistings.utils.isScrolled
import com.google.accompanist.insets.navigationBarsPadding

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
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current
        ArticleScreenContent(
            anime = anime,
            // Allow opening the Drawer if the screen is not expanded
            navigationIconContent = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_up),
                        tint = MaterialTheme.colors.primary
                    )
                }
            },
            // Show the bottom bar if the screen is not expanded
            bottomBarContent = {
                BottomBar(
                    onSharePost = { sharePost(anime, context) },
                    modifier = Modifier.navigationBarsPadding(start = false, end = false)
                )
            },
            lazyListState = lazyListState
        )
    }
}

/**
 * Stateless Article Screen that displays a single post.
 *
 * @param post (state) item to display
 * @param navigationIconContent (UI) content to show for the navigation icon
 * @param bottomBarContent (UI) content to show for the bottom bar
 */
@Composable
private fun ArticleScreenContent(
    anime: Anime,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    lazyListState: LazyListState = rememberLazyListState()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_drawer),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.category, anime.type),
                            style = MaterialTheme.typography.subtitle2,
                            color = LocalContentColor.current,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .weight(1.5f)
                        )
                    }
                },
                navigationIcon = navigationIconContent,
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp,
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        bottomBar = bottomBarContent
    ) { innerPadding ->
        ListingContent(
            anime = anime,
            modifier = Modifier
                // innerPadding takes into account the top and bottom bar
                .padding(innerPadding),
            state = lazyListState,
        )
    }
}

/**
 * Bottom bar for Article screen
 *
 * @param onSharePost (event) request this post to be shared
 */
@Composable
private fun BottomBar(
    onSharePost: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            ShareButton(onClick = onSharePost)
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