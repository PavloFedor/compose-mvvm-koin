package com.pavlo.fedor.compose.flow.base

import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType

sealed class Argument<Type> : (NavBackStackEntry) -> Type {
    abstract val type: NavType<Type>
    abstract val key: String

    override fun invoke(entry: NavBackStackEntry): Type = entry.safeArgument(key, type)

    class StringArgument(override val key: String) : Argument<String?>() {
        override val type: NavType<String?> = NavType.StringType
    }

    object NotingType : Argument<Unit>() {
        override val type: NavType<Unit> = object : NavType<Unit>(isNullableAllowed = true) {
            override fun get(bundle: Bundle, key: String): Unit? {
                TODO("Not yet implemented")
            }

            override fun parseValue(value: String) {
                TODO("Not yet implemented")
            }

            override fun put(bundle: Bundle, key: String, value: Unit) {
                TODO("Not yet implemented")
            }
        }
        override val key: String = "Nothing"

        override fun invoke(entry: NavBackStackEntry) = Unit
    }
}
