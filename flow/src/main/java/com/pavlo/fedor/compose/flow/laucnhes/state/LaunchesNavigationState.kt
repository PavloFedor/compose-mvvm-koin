package com.pavlo.fedor.compose.flow.laucnhes.state

import com.pavlo.fedor.compose.ui.widget.NavigationCell

internal interface LaunchesNavigationState {
    val navigationCells: List<NavigationCell>
    val selectedCell: NavigationCell
}