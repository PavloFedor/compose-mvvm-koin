package com.pavlo.fedor.compose.flow.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.core.scope.Scope
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KClass

@Composable
fun KoinScope(scopeId: String, argsScopeId: String? = null, qualifier: Qualifier, scoped: @Composable Scope.() -> Unit) {
    val mainScope: Scope = remember {
        GlobalContext.get().getOrCreateScope(scopeId = scopeId, qualifier = qualifier)
    }
    viewModel<ScopeHandleViewModel>(
        factory = ScopeHandleViewModelFactory {
            mutableListOf(mainScope)
                .apply { if (argsScopeId != null) add(argsScope(argsScopeId)) }
        }
    )
    scoped(mainScope)
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
fun argsQualifier() = typed(ArgsProvider::class)
fun argsId(argsScopeId: String) = "${argsScopeId}_args"
fun argsScope(argsScopeId: String) = GlobalContext.get().getOrCreateScope(argsId(argsScopeId), argsQualifier())
fun args(argsScopeId: String): ParametersHolder = argsScope(argsScopeId).get<ArgsProvider>()