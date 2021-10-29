package com.pavlo.fedor.compose.service.getaway.error

interface GetawayError {
    val cause: Throwable?
    val message: String?
}