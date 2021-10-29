package com.pavlo.fedor.compose.service.getaway.api.base

internal interface ApiProvider {

    fun <Api : Any> provide(apiClazz: Class<Api>): Api
}