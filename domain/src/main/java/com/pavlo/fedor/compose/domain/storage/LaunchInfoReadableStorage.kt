package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import kotlin.reflect.KProperty

@JvmInline
value class LaunchId(private val value: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
}

interface LaunchInfoReadableStorage : SearchableStorage<LaunchId, LaunchInfo?>