package com.pavlo.fedor.compose.ui.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.util.*

data class NavigationCell(
    val id: String = UUID.randomUUID().toString(),
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int
)

@Composable
fun BottomBar(items: List<NavigationCell>, selectedItem: NavigationCell, onItemsClick: (NavigationCell) -> Unit) = BottomNavigation(elevation = 4.dp) {
    items.forEach { cell ->
        BottomBarItem(navigationItem = cell, onItemsClick = onItemsClick, isSelected = cell.id == selectedItem.id)
    }
}

@Composable
fun RowScope.BottomBarItem(navigationItem: NavigationCell, isSelected: Boolean, onItemsClick: (NavigationCell) -> Unit) = BottomNavigationItem(
    icon = { Icon(painterResource(navigationItem.iconRes), stringResource(navigationItem.labelRes)) },
    label = { Text(text = stringResource(id = navigationItem.labelRes)) },
    selected = isSelected,
    onClick = { onItemsClick(navigationItem) },
    selectedContentColor = MaterialTheme.colors.secondary,
    unselectedContentColor = MaterialTheme.colors.primaryVariant
)