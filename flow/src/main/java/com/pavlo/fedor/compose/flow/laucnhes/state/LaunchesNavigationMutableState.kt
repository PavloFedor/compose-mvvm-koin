package com.pavlo.fedor.compose.flow.laucnhes.state

import com.pavlo.fedor.compose.ui.widget.NavigationCell

internal data class LaunchesNavigationMutableState(
    override val navigationCells: List<NavigationCell>,
    override val selectedCell: NavigationCell
) : LaunchesNavigationState