package com.pavlo.fedor.compose.flow.base

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import java.lang.IllegalArgumentException
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <Type> NavBackStackEntry.safeArgument(key: String, navType: NavType<Type>): Type {
    return (arguments?.get(key) as? Type) ?: throw IllegalArgumentException("Args with key $key shouldn't be null")
}

fun <T> NavController.navigate(screen: Screen<String>, params: T) {
    val scopeId = UUID.randomUUID().toString()
    argsScope(scopeId).get<ArgsProvider> { parametersOf(params) }
    navigate(screen.route(scopeId))
}
