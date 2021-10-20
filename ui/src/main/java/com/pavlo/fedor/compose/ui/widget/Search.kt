package com.pavlo.fedor.compose.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.ui.R

@Composable
fun Search(text: String, onSearchChanged: (String) -> Unit) = Surface(color = Color.White, elevation = 8.dp) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        Color.White,
        darkIcons = true
    )
    Row(modifier = Modifier.height(48.dp).fillMaxWidth()) {
        Field(text, onSearchChanged)
    }
}


@Composable
private fun Field(text: String, onSearchChanged: (String) -> Unit) = TextField(
    value = text,
    singleLine = true,
    onValueChange = onSearchChanged,
    placeholder = { Text(text = "Search", fontSize = 12.sp, color = Color(0xFFA2A7A6)) },
    textStyle = TextStyle(fontSize = 12.sp),
    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
    colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.White,
        textColor = Color.Black,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = Color.Black
    ),

    leadingIcon = { FieldIcon(R.drawable.ic_search) },
    trailingIcon = { FieldIcon(R.drawable.ic_clear) { onSearchChanged("") } },
)

@Composable
private fun FieldIcon(@DrawableRes iconRes: Int, onClick: (() -> Unit)? = null) = Icon(
    painter = painterResource(id = iconRes),
    tint = Color(0xFFA2A7A6),
    contentDescription = null,
    modifier = if (onClick != null) Modifier.clickable {
        onClick()
    } else Modifier
)
