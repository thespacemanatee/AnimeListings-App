package com.example.animelistings.ui.listing_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.animelistings.domain.Anime

private val defaultSpacerSize = 16.dp

@Composable
fun ListingContent(
    anime: Anime,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = defaultSpacerSize),
        state = state,
    ) {
        listingContentItems(anime)
    }
}

fun LazyListScope.listingContentItems(anime: Anime) {
    item {
        Spacer(Modifier.height(defaultSpacerSize))
        ListingHeaderImage(anime)
    }
    item {
        Text(text = anime.title, style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(8.dp))
    }
//    anime.subtitle?.let { subtitle ->
//        item {
//            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                Text(
//                    text = subtitle,
//                    style = MaterialTheme.typography.body2,
//                    lineHeight = 20.sp
//                )
//            }
//            Spacer(Modifier.height(defaultSpacerSize))
//        }
//    }
//    item {
//        PostMetadata(anime.metadata)
//        Spacer(Modifier.height(24.dp))
//    }
//    items(anime.paragraphs) {
//        Paragraph(paragraph = it)
//    }
    item {
        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun ListingHeaderImage(anime: Anime) {
    val imageModifier = Modifier
        .heightIn(min = 180.dp)
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.medium)
    Image(
        painter = rememberImagePainter(data = anime.imageUrl),
        contentDescription = null, // decorative
        modifier = imageModifier,
        contentScale = ContentScale.Crop
    )
    Spacer(Modifier.height(defaultSpacerSize))
}

