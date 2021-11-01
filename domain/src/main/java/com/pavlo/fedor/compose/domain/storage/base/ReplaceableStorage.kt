package com.pavlo.fedor.compose.domain.storage.base

interface ReplaceableStorage<in Value, out RValue> {

    suspend fun replace(value: Value): RValue
}
