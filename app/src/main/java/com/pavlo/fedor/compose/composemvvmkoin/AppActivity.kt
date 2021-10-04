package com.pavlo.fedor.compose.composemvvmkoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pavlo.fedor.compose.composemvvmkoin.base.viewModel
import com.pavlo.fedor.compose.composemvvmkoin.laucnhes.details.LaunchDetailsViewModel
import com.pavlo.fedor.compose.ui.R.*
import com.pavlo.fedor.compose.ui.theme.ComposeMVVMKoinTheme

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMVVMKoinTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = drawable.ic_empty_state),
                            contentDescription = "Empty state"
                        )
                    }
                }
            }
        }
    }
}

