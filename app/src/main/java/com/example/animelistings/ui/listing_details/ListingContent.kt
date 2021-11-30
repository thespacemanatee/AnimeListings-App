package com.example.animelistings.ui.listing_details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.animelistings.domain.Anime
import com.example.animelistings.ui.listing_details.ListingDetailsConstants.appBarExpandedHeight

@Composable
fun ListingContent(
    anime: Anime,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        contentPadding = PaddingValues(top = appBarExpandedHeight),
        state = state,
    ) {
        item {  }
    }
}
