package com.pavlo.fedor.compose.domain.storage.base

interface ReadableStorage<out Value> {
    suspend fun get(): Value
}