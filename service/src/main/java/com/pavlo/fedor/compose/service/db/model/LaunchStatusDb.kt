package com.pavlo.fedor.compose.service.db.model

enum class LaunchStatusDb(val value: Int) {
    SUCCEED(1), FAILED(2), IN_PROGRESS(0)
}