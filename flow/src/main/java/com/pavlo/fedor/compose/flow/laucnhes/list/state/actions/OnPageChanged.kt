package com.pavlo.fedor.compose.flow.laucnhes.list.state.actions

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.Page
import com.pavlo.fedor.compose.domain.model.PageResult

class OnPageChanged(val newPage: Page<LaunchInfo>) : LaunchesListStateAction