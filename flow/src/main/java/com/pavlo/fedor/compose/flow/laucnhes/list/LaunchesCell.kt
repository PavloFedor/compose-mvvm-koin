package com.pavlo.fedor.compose.flow.laucnhes.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.flow.R
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState
import com.pavlo.fedor.compose.flow.laucnhes.list.state.LaunchesListItemState.Progress
import com.pavlo.fedor.compose.ui.widget.LoaderCell
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import java.text.SimpleDateFormat
import java.util.*

private const val LAUNCH_IMAGE = "launch_image"
private const val DIVIDER = "divider"
private const val LAUNCH_IS_FAVORITE = "is_favorite_icon"
private const val LAUNCH_TITLE = "launch_title"
private const val LAUNCH_FLAG_IMAGE = "launch_flag"
private const val LAUNCH_DATE = "date"
private const val LAUNCH_SUCCESS_INDICATOR = "indicator"
private const val LAUNCH_SUCCESS_TITLE = "success"


@Composable
fun LaunchInfoCell(info: LaunchInfo, onItemClick: () -> Unit, onFavoriteClick: () -> Unit) = Row(
    modifier = Modifier.padding(PaddingValues(bottom = 16.dp, start = 4.dp, end = 4.dp))
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
    ) {
        ConstraintLayout(
            constraints(),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            LaunchImage(infoUrl = info.imageUrl)

            Divider(
                color = Color(0xFFA6A6A6),
                modifier = Modifier
                    .layoutId(DIVIDER)
                    .height(0.5.dp)
            )

            FavoriteButton(info.isFavorite, onFavoriteClick)

            LaunchTitle(title = info.name)
            FlagImage(flagLink = info.countryFlagLink)
            LaunchDate(date = info.date)
            LaunchSuccessIndicator(isSuccess = info.isSucceed)
        }
    }
}

@Composable
private fun LaunchImage(infoUrl: String) {
    GlideImage(
        imageModel = infoUrl,
        contentScale = ContentScale.FillWidth,
        failure = { LaunchErrorImage() },
        modifier = Modifier
            .layoutId(LAUNCH_IMAGE)
            .aspectRatio(1.6f),
    )
}

@Composable
private fun LaunchErrorImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_placeholder),
        contentDescription = "placeholder",
        alignment = Alignment.Center,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}

@Composable
private fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) = FloatingActionButton(
    onClick = onClick,
    modifier = Modifier
        .layoutId(LAUNCH_IS_FAVORITE)
        .size(40.dp),
    backgroundColor = MaterialTheme.colors.primary
) {
    Icon(
        painter = painterResource(R.drawable.ic_favorite_tab),
        contentDescription = "favorite",
        tint = if (isFavorite) Color(0xFF4386E8) else Color(0xFFA6A6A6)
    )
}

@Composable
private fun LaunchTitle(title: String) {

    val fontSize = 16
    val lineHeight = remember {
        fontSize * 4 / 3
    }
    val height = remember {
        lineHeight * 2
    }

    Text(
        text = title,
        color = Color.Black,
        fontSize = fontSize.sp,
        modifier = Modifier
            .height(height.dp)
            .layoutId(LAUNCH_TITLE),
        maxLines = 2,
        textAlign = TextAlign.Start,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        lineHeight = lineHeight.sp
    )
}


@Composable
private fun FlagImage(flagLink: String) {
    GlideImage(
        imageModel = flagLink,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .layoutId(LAUNCH_FLAG_IMAGE)
            .size(20.dp),
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_flag_place_holder),
                contentDescription = "placeholder",
                alignment = Alignment.Center,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    )
}

private const val DATE_SHORT_FORMAT = "dd/MM/yy"

@Composable
private fun LaunchDate(date: Long) {
    val formattedDte = remember {
        SimpleDateFormat(DATE_SHORT_FORMAT, Locale.getDefault()).also { formater ->
            formater.timeZone = TimeZone.getTimeZone("GTM+00:00")
        }
    }
    Text(
        text = formattedDte.format(date),
        color = Color(0xFFA6A6A6),
        fontSize = 12.sp,
        modifier = Modifier.layoutId(LAUNCH_DATE),
        maxLines = 1,
        textAlign = TextAlign.Start,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
    )
}

@Composable
private fun LaunchSuccessIndicator(isSuccess: Boolean) {
    val icon = if (isSuccess) R.drawable.ic_launch_succeed else R.drawable.ic_launch_failed
    val textColor = if (isSuccess) Color(0xFF22BEC8) else Color(0xFFE3576E)
    val text = if (isSuccess) "Success" else "Failed"

    Image(
        painter = painterResource(id = icon),
        contentDescription = "indicator",
        modifier = Modifier.layoutId(LAUNCH_SUCCESS_INDICATOR)
    )

    Text(
        text = text,
        color = textColor,
        maxLines = 1,
        textAlign = TextAlign.Start,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        modifier = Modifier.layoutId(LAUNCH_SUCCESS_TITLE)
    )
}

private fun constraints() = ConstraintSet {
    val launchesImage = createRefFor(LAUNCH_IMAGE)
    val divider = createRefFor(DIVIDER)
    val favoriteButton = createRefFor(LAUNCH_IS_FAVORITE)
    val title = createRefFor(LAUNCH_TITLE)
    val flag = createRefFor(LAUNCH_FLAG_IMAGE)
    val date = createRefFor(LAUNCH_DATE)
    val indicator = createRefFor(LAUNCH_SUCCESS_INDICATOR)
    val successTitle = createRefFor(LAUNCH_SUCCESS_TITLE)

    constrain(launchesImage) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }
    constrain(divider) {
        top.linkTo(launchesImage.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }
    constrain(favoriteButton) {
        top.linkTo(divider.top)
        bottom.linkTo(divider.bottom)
        end.linkTo(parent.end, margin = 16.dp)
    }
    constrain(title) {
        top.linkTo(divider.bottom, margin = 16.dp)
        start.linkTo(parent.start, margin = 16.dp)
        end.linkTo(favoriteButton.start, margin = 16.dp)
        width = Dimension.fillToConstraints
    }
    constrain(flag) {
        top.linkTo(date.top)
        bottom.linkTo(date.bottom)
        start.linkTo(parent.start, margin = 16.dp)
    }
    constrain(date) {
        top.linkTo(title.bottom, margin = 8.dp)
        start.linkTo(flag.end, margin = 8.dp)
    }
    constrain(successTitle) {
        end.linkTo(parent.end, margin = 16.dp)
        top.linkTo(date.top)
    }
    constrain(indicator) {
        end.linkTo(successTitle.start, margin = 8.dp)
        top.linkTo(successTitle.top)
        bottom.linkTo(successTitle.bottom)
    }
}
