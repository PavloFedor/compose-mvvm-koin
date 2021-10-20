package com.pavlo.fedor.compose.flow.base

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
fun <Type> NavBackStackEntry.safeArgument(key: String, navType: NavType<Type>): Type {
    return (arguments?.get(key) as? Type) ?: throw IllegalArgumentException("Args with key $key shouldn't be null")
}

