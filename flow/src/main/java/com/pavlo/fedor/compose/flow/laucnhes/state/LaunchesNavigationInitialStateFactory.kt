package com.pavlo.fedor.compose.flow.laucnhes.state

import com.pavlo.fedor.compose.flow.R
import com.pavlo.fedor.compose.flow.base.state.InitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.list.favorite.FavoriteLaunchesListScreen
import com.pavlo.fedor.compose.flow.laucnhes.list.history.HistoryLaunchesListScreen
import com.pavlo.fedor.compose.ui.widget.NavigationCell

internal class LaunchesNavigationInitialStateFactory : InitialStateFactory<LaunchesNavigationMutableState> {
    override fun invoke(): LaunchesNavigationMutableState {
        val navigationCell = listOf(
            NavigationCell(id = HistoryLaunchesListScreen.host, iconRes = R.drawable.ic_launches_tab, labelRes = R.string.launches_history_tab),
            NavigationCell(id = FavoriteLaunchesListScreen.host, iconRes = R.drawable.ic_favorite_tab, labelRes = R.string.launches_favorite_tab)
        )
        return LaunchesNavigationMutableState(
            navigationCells = navigationCell,
            selectedCell = navigationCell.first()
        )
    }
}