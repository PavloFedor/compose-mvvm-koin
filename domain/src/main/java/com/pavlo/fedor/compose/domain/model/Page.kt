package com.pavlo.fedor.compose.domain.model

data class Page<Entity>(
    val offset: Int,
    val total: Int,
    val entities: List<Entity>
)
