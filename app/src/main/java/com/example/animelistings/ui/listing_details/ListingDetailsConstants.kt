package com.example.animelistings.ui.listing_details

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ListingDetailsConstants {
    val AppBarCollapsedHeight = 56.dp
    val AppBarExpandedHeight = 400.dp
    val AppBarHorizontalPadding = 4.dp
    // Start inset for the title when there is no navigation icon provided
    val TitleInsetWithoutIcon = Modifier.width(16.dp - AppBarHorizontalPadding)
    // Start inset for the title when there is a navigation icon provided
    val TitleIconModifier = Modifier.fillMaxHeight()
        .width(72.dp - AppBarHorizontalPadding)

    // The gap on all sides between the FAB and the cutout
    val BottomAppBarCutoutOffset = 8.dp
    // How far from the notch the rounded edges start
    val BottomAppBarRoundedEdgeRadius = 4.dp
}
