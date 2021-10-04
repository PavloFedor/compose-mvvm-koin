package com.pavlo.fedor.compose.composemvvmkoin.base

import androidx.navigation.NavController

fun <Args> NavController.navigate(screen: Screen) {
    navigate(screen.route)
}