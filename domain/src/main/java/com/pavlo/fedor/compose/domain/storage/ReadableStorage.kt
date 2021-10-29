package com.pavlo.fedor.compose.domain.storage

interface ReadableStorage<out Value> {
    suspend fun get(): Value?
}