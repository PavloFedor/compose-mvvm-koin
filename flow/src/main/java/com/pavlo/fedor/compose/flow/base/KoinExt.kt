package com.pavlo.fedor.compose.flow.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.androidx.compose.getKoin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import java.lang.IllegalStateException
import kotlin.reflect.KClass

@Composable
fun KoinScope(scopeId: String, qualifier: Qualifier, scoped: @Composable Scope.() -> Unit) {
    scoped(getKoin().getOrCreateScope(scopeId = scopeId, qualifier = qualifier))
}

@Composable
inline fun <reified VM : ViewModel> Scope.scopedViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): VM = let { scope ->
    val owner = LocalViewModelStoreOwner.current
        ?: throw IllegalStateException("Store owner shouldn't be null")
    remember(qualifier, parameters) {
        scope.getViewModel(
            owner = { ViewModelOwner.from(owner) },
            qualifier = qualifier,
            parameters = parameters
        )
    }
}

fun <T : Any> typed(type: KClass<T>): Qualifier = TypeQualifier(type = type)