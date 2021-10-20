package com.pavlo.fedor.compose.flow.base

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import java.util.*

abstract class Screen<ArgsType>(
    route: String,
    private val argsType: Argument<ArgsType>,
    parentRoute: String? = null
) {

    val host: String = HOST_TEMPLATE.format(parentRoute?.plus("/$route") ?: route)

    companion object {

        private const val HOST_TEMPLATE = "app://screen/%s/"

        fun hostFrom(route: String): String {
            val argsIndex = route.indexOf("/?")
            return if (argsIndex == -1) {
                route
            } else {
                route.substring(0, argsIndex)
            }
        }
    }

    @Composable
    protected abstract fun Content(args: ArgsType, scopeId: String, parentNavController: NavController)

    fun registerRoute(navController: NavController): NavGraphBuilder.() -> Unit = {
        val args: (NavBackStackEntry) -> ArgsType = argsType::invoke
        val content: @Composable (NavBackStackEntry) -> Unit = {
            Content(args = args(it), scopeId = UUID.randomUUID().toString(), parentNavController = navController)
        }
        val arguments = mutableListOf<NamedNavArgument>()
        val route = buildRoute { arguments.add(navArgument(argsType.key) { type = NavType.StringType }) }
        composable(route = route, content = content, arguments = arguments)
    }

    fun route(args: ArgsType) = if (argsType is Argument.NotingType) {
        host
    } else {
        host.plus("&${argsType.key}=$args")
    }

    private fun buildRoute(onHasArguments: () -> Unit = {}): String {
        return if (argsType is Argument.NotingType) {
            host
        } else {
            onHasArguments()
            host.plus("&${argsType.key}={${argsType.key}}")
        }
    }

    override fun toString(): String = buildRoute()
}
