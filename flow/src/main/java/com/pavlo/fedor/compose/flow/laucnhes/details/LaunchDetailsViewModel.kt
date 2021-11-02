package com.pavlo.fedor.compose.flow.laucnhes.details

import com.pavlo.fedor.compose.flow.base.BaseViewModel
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsState

abstract class LaunchDetailsViewModel : BaseViewModel<LaunchInfoDetailsState>() {

    abstract fun onFavoriteChanged()
}
