package com.pavlo.fedor.compose.service.sotrage.error

sealed class StorageError(final override val cause: Throwable? = null) : Throwable() {

    object NoFound : StorageError() {
        override val message: String = "Item not found"
    }
}