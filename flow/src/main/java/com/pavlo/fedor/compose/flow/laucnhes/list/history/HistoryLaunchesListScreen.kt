package com.pavlo.fedor.compose.flow.laucnhes.list.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pavlo.fedor.compose.flow.R
import com.pavlo.fedor.compose.flow.base.*
import com.pavlo.fedor.compose.flow.laucnhes.list.LaunchesList
import com.pavlo.fedor.compose.ui.widget.LoaderCell
import java.util.*

object HistoryLaunchesListScreen : Screen<Unit>(parentRoute = "launches", route = "history", argsType = Argument.NotingType) {

    @Composable
    override fun Content(args: Unit, scopeId: String, parentNavController: NavController) = KoinScope(scopeId = UUID.randomUUID().toString(), qualifier = typed(HistoryLaunchesListScreen::class)) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            Color.White,
            darkIcons = true
        )

        Layout(viewModel = scopedViewModel())
    }

    @Composable
    private fun Layout(viewModel: HistoryLaunchesListViewModel) = Column(
        Modifier.fillMaxWidth()
    ) {

        val state by viewModel.stateFlow.collectAsState()

        Surface(color = Color.White, elevation = 8.dp) {
            Row(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
            ) {
                TextField(
                    modifier = Modifier
                        .absolutePadding(left = 16.dp, right = 48.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    value = state.searchText,
                    singleLine = true,
                    onValueChange = { query -> viewModel.onSearchChanged(query = query) },
                    placeholder = { Text(text = "Search", fontSize = 12.sp) },
                    textStyle = TextStyle(fontSize = 12.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                )
            }
        }

        when {
            state.shouldShowEmptyView -> EmptyView()
            state.isDataLoading && state.items.isEmpty() -> LoaderCell(true)
            else -> LaunchesList(
                state = state,
                onItemClick = { },
                onFavoriteClick = { item -> viewModel.onFavorite(item) },
                onLoadMore = { viewModel.onListScrolledToBottom() },
                onRefresh = { viewModel.onRefresh() }
            )
        }
    }

    @Composable
    private fun EmptyView() = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_state),
            contentDescription = "emptyView",
            contentScale = ContentScale.Inside,
        )
        Text(
            text = "No results found",
            color = Color(0xFFA6A6A6),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
