package com.pavlo.fedor.compose.domain.model

interface Page<Entity> {
    val offset: Long
    val total: Long
    val entities: List<Entity>
    val isLastPage: Boolean
}
