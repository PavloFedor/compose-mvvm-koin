package com.pavlo.fedor.compose.composemvvmkoin.base

import androidx.compose.runtime.Composable

abstract class Screen(val route: String)  {

    @Composable
    abstract fun Content()
}