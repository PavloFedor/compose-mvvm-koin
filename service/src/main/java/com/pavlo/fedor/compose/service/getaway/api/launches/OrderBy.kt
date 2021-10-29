package com.pavlo.fedor.compose.service.getaway.api.launches

sealed class OrderBy(private val value: String) : (Direction) -> String {

    final override fun invoke(direction: Direction): String = direction() + value

    object Date : OrderBy("net")
}

sealed class Direction(private val value: String) : () -> String {


    override fun invoke(): String = value

    object Desc : Direction("-")

    object Asc : Direction("")
}
