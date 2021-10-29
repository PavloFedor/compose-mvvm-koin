package com.pavlo.fedor.compose.domain.storage

interface SearchableStorage<Query, Value> {
    fun search(query: Query): Value
}
