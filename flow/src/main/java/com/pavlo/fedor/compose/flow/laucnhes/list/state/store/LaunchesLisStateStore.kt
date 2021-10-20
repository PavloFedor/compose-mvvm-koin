package com.pavlo.fedor.compose.flow.laucnhes.list.state.store

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.flow.base.state.BaseSyncStateStore
import com.pavlo.fedor.compose.flow.base.state.InitialStateFactory
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState.InfoItem
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState.Progress
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.actions.*

abstract class LaunchesLisStateStore<StateValue : LaunchesListState, MStateValue : StateValue>(
    initialStateFactory: InitialStateFactory<MStateValue>
) : BaseSyncStateStore<StateValue, MStateValue, LaunchesListStateAction>(initialStateFactory) {

    final override suspend fun onAction(oldState: MStateValue, action: LaunchesListStateAction): MStateValue {
        return when (action) {
            is OnPageChanged -> oldState.onPageChanged(action.newPage)
            is OnItemChange -> oldState.onItemChanged(action.launchInfo)
            is OnNewPageLoadingChanged -> oldState.onNewChangeLoading(action.isLoading)
            is OnDataLoadingChanged -> oldState.onDataLoadingStateChanged(action.isLoading)
            else -> oldState.onOtherAction(action = action)
        }
    }

    protected abstract fun MStateValue.onPageChanged(offset: Int, total: Int, count: Int, items: List<LaunchesListItemState>): MStateValue

    protected abstract fun MStateValue.onItemChanged(index: Int, updatedItem: LaunchesListItemState): MStateValue

    protected abstract fun MStateValue.updateItems(items: List<LaunchesListItemState>): MStateValue

    protected abstract fun MStateValue.onDataLoadingStateChanged(isLoading: Boolean): MStateValue

    protected open fun MStateValue.onOtherAction(action: LaunchesListStateAction): MStateValue = this

    private fun MStateValue.onPageChanged(page: Page<LaunchInfo>): MStateValue {
        val items = page.entities.map { InfoItem(it) }
        return onPageChanged(offset = page.offset, total = page.total, count = page.entities.size, items = items)
    }

    private fun MStateValue.onItemChanged(infoItem: LaunchInfo): MStateValue {
        val itemIndex = items.indexOfFirst { (it as? InfoItem)?.info?.id == infoItem.id }
        return if (itemIndex != -1) {
            onItemChanged(itemIndex, InfoItem(infoItem))
        } else {
            this
        }
    }

    private fun MStateValue.onNewChangeLoading(isLoading: Boolean): MStateValue {
        if (items.isEmpty() || isDataLoading) return this

        val newItems = when {
            !isLoading && items.lastOrNull() is Progress -> items.toMutableList().also { it.removeLast() }
            isLoading && items.lastOrNull() !is Progress -> items.toMutableList().also { it.add(Progress) }
            else -> null
        }
        return newItems?.let { updateItems(it) } ?: this
    }
}
