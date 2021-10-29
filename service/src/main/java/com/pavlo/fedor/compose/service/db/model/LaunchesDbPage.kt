package com.pavlo.fedor.compose.service.db.model

internal data class LaunchesDbPage(
    val total: Long,
    val launches: List<LaunchInfoDb>
)