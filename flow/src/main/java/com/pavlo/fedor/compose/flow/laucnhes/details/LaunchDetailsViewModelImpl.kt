package com.pavlo.fedor.compose.flow.laucnhes.details

import com.pavlo.fedor.compose.domain.usecase.ToggleFavoriteStateUseCase
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsState
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsStateAction
import com.pavlo.fedor.compose.flow.laucnhes.details.state.LaunchInfoDetailsStateStore
import kotlinx.coroutines.flow.*

class LaunchDetailsViewModelImpl(
    private val stateStore: LaunchInfoDetailsStateStore,
    private val toggleFavoriteStateUseCase: ToggleFavoriteStateUseCase
) : LaunchDetailsViewModel() {
    override val stateFlow: StateFlow<LaunchInfoDetailsState> = stateStore.state

    override fun onFavoriteChanged() = launch {
        flowOf(stateStore.state.value)
            .map { it.launchInfo }
            .flatMapConcat { item -> toggleFavoriteStateUseCase(item).map { item.copy(isFavorite = !item.isFavorite) } }
            .collect { stateStore.dispatch(LaunchInfoDetailsStateAction.OnItemChanged(it)) }
    }
}
