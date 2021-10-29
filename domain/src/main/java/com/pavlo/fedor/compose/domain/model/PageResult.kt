package com.pavlo.fedor.compose.domain.model

class PageResult<Entity>(
    val offset: Long,
    val total: Long,
    val entities: List<Entity>
)