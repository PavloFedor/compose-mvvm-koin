package com.pavlo.fedor.compose.flow.laucnhes.details

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.ui.R


object LaunchDetailsScreen : Screen<String>(parentRoute = "launches", route = "details", argsType = Argument.StringArgument(key = "scopeId")) {

    @Composable
    override fun Content(args: String, scopeId: String, parentNavController: NavController) = KoinScope(
        scopeId = scopeId,
        argsScopeId = args,
        qualifier = typed(args(args).get<DetailArgs>()::class)
    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            MaterialTheme.colors.primary,
            darkIcons = true
        )
        val viewModel: LaunchDetailsViewModel = scopedViewModel {
            args(args)
        }
        val state by viewModel.stateFlow.collectAsState()
        Scaffold(
            topBar = {
                Toolbar(
                    launchName = state.launchInfo.name,
                    isFavorite = state.launchInfo.isFavorite,
                    navController = parentNavController,
                    favAction = viewModel::onFavoriteChanged
                )
            },
            content = {
                Page(wikiUrl = state.launchInfo.infoPage)
            }
        )
    }

    @Composable
    private fun Toolbar(launchName: String, isFavorite: Boolean, navController: NavController, favAction: () -> Unit) = TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(
                text = launchName,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.padding(end = 24.dp),
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
        },
        navigationIcon = {
            Icon(
                tint = Color(0xFFA1A7A6),
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "back",
                modifier = Modifier.padding(start = 16.dp).clickable { navController.popBackStack() }
            )
        },
        actions = {
            Icon(
                painter = painterResource(com.pavlo.fedor.compose.flow.R.drawable.ic_favorite_tab),
                contentDescription = "favorite",
                tint = if (isFavorite) Color(0xFF4386E8) else Color(0xFFA6A6A6),
                modifier = Modifier.padding(end = 16.dp).clickable { favAction() }
            )
        }
    )

    @Composable
    private fun Page(wikiUrl: String) = AndroidView(
        factory = this::wevView,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        it.loadUrl(wikiUrl)
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun wevView(context: Context) = WebView(context).also { webView ->
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
    }
}
