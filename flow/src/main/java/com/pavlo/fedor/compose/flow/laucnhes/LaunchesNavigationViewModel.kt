package com.pavlo.fedor.compose.flow.laucnhes

import com.pavlo.fedor.compose.flow.base.BaseViewModel
import com.pavlo.fedor.compose.flow.laucnhes.state.LaunchesNavigationState
import com.pavlo.fedor.compose.ui.widget.NavigationCell
import kotlinx.coroutines.flow.flow

internal abstract class LaunchesNavigationViewModel : BaseViewModel<LaunchesNavigationState>() {

    abstract fun onSelected(screenId: String)
}