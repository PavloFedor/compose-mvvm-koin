package com.pavlo.fedor.compose.flow

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.laucnhes.LaunchesNavigationScreen
import com.pavlo.fedor.compose.ui.theme.ComposeMVVMKoinTheme

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeMVVMKoinTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setNavigationBarColor(
                    MaterialTheme.colors.primary,
                    darkIcons = true,
                )
                ProvideWindowInsets {
                    Content()
                }
            }
        }
    }

    @Composable
    private fun Content() = Surface(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsWithImePadding()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LaunchesNavigationScreen.toString(),
        ) {
            LaunchesNavigationScreen.registerRoute(navController)(this)
        }
    }
}

