package com.pavlo.fedor.compose.flow.laucnhes.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pavlo.fedor.compose.core.DatePattern
import com.pavlo.fedor.compose.domain.model.LaunchInfo
import com.pavlo.fedor.compose.domain.model.LaunchStatus
import com.pavlo.fedor.compose.flow.R
import com.skydoves.landscapist.glide.GlideImage
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
        modifier = Modifier.fillMaxWidth().clickable(onClick = onItemClick)
    ) {
        ConstraintLayout(
            constraints(),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            LaunchImage(infoUrl = info.imageUrl ?: "")

            Divider(
                color = Color(0xFFA6A6A6),
                modifier = Modifier.layoutId(DIVIDER).height(0.5.dp)
            )

            FavoriteButton(info.isFavorite, onFavoriteClick)

            LaunchTitle(title = info.name)
            FlagImage(flagLink = info.countryFlagLink)
            LaunchDate(date = info.date)
            LaunchSuccessIndicator(status = info.status)
        }
    }
}

@Composable
private fun LaunchImage(infoUrl: String) {
    GlideImage(
        imageModel = infoUrl,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        failure = { LaunchErrorImage() },
        modifier = Modifier.layoutId(LAUNCH_IMAGE).aspectRatio(1.6f),
        requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .sizeMultiplier(0.2f)
    )
}

@Composable
private fun LaunchErrorImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_placeholder),
        contentDescription = "placeholder",
        alignment = Alignment.Center,
        contentScale = ContentScale.Inside,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
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
        modifier = Modifier.height(height.dp).layoutId(LAUNCH_TITLE),
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
        modifier = Modifier.layoutId(LAUNCH_FLAG_IMAGE).size(20.dp),
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_flag_place_holder),
                contentDescription = "placeholder",
                alignment = Alignment.Center,
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            )
        }
    )
}


@Composable
private fun LaunchDate(date: Long) {
    Text(
        text = DatePattern.SHORT_DATE(date = Date(date)),
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
private fun LaunchSuccessIndicator(status: LaunchStatus) {
    val (icon, text, textColor) = when (status) {
        LaunchStatus.SUCCEED -> Triple(R.drawable.ic_launch_succeed, R.string.launch_status_indicator_succeed, Color(0xFF22BEC8))
        LaunchStatus.FAILED -> Triple(R.drawable.ic_launch_failed, R.string.launch_status_indicator_failed, Color(0xFFE3576E))
        LaunchStatus.IN_PROGRESS -> Triple(R.drawable.ic_lauch_in_progress, R.string.launch_status_indicator_in_progress, Color(0xFFECB900))
    }

    Image(
        painter = painterResource(id = icon),
        contentDescription = "indicator",
        modifier = Modifier.layoutId(LAUNCH_SUCCESS_INDICATOR)
    )

    Text(
        text = stringResource(id = text),
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
