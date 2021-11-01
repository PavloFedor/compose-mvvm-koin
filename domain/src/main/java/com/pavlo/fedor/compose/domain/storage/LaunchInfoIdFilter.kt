package com.pavlo.fedor.compose.domain.storage

import com.pavlo.fedor.compose.domain.model.LaunchInfo
import kotlin.reflect.KProperty

class LaunchInfoIdFilter(id: LaunchId) : Filter<LaunchInfo> {

    private val id by id

    override fun invoke(info: LaunchInfo): Boolean = info.id == id

    @JvmInline
    value class LaunchId(val value: String) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
    }
}