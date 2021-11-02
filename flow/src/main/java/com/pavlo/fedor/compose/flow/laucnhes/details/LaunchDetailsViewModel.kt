package com.pavlo.fedor.compose.flow.laucnhes.details

import androidx.lifecycle.ViewModel
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LaunchDetailsViewModel(private val launchInfo: LaunchInfo) : BaseViewModel<String>() {
    override val stateFlow: StateFlow<String>  = MutableStateFlow(launchInfo.name)
}