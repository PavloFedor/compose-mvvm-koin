package com.pavlo.fedor.compose.domain.storage.base

interface SelectableStorage<Query, Value> {
    fun select(query: Query): Value
}
