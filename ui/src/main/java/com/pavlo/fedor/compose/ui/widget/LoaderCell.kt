package com.pavlo.fedor.compose.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoaderCell(fillLayout: Boolean = false) = Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .padding(bottom = 16.dp)
        .fillMaxWidth()
        .run { if (fillLayout) this.fillMaxHeight() else this }
) {
    CircularProgressIndicator(
        modifier = Modifier.size(if (fillLayout) 56.dp else 36.dp)
    )
}
