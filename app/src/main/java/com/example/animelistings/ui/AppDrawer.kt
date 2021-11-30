package com.example.animelistings.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animelistings.R
import com.example.animelistings.ui.components.AnimeListingsIcon
import com.example.animelistings.ui.components.NavigationIcon
import com.example.animelistings.ui.theme.AnimeListingsTheme

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        AnimeListingsLogo(Modifier.padding(16.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        DrawerButton(
            icon = Icons.Filled.Home,
            label = stringResource(id = R.string.home_title),
            isSelected = currentRoute == AnimeListingsDestinations.HOME_ROUTE,
            action = {
                navigateToHome()
                closeDrawer()
            }
        )
    }
}

@Composable
private fun AnimeListingsLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        AnimeListingsIcon()
        Spacer(Modifier.width(8.dp))
//        Image(
//            painter = painterResource(R.drawable.ic_drawer),
//            contentDescription = stringResource(R.string.app_name),
//            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
//        )
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null, // decorative
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    AnimeListingsTheme {
        Surface {
            AppDrawer(
                currentRoute = AnimeListingsDestinations.HOME_ROUTE,
                navigateToHome = {},
                closeDrawer = { }
            )
        }
    }
}
