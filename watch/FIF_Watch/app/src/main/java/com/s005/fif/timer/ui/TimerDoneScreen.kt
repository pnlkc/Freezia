package com.s005.fif.timer.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.utils.ColorCombineTextUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import com.s005.fif.utils.VibrateUtil
import kotlinx.coroutines.delay

@Composable
fun TimerDoneScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    var alpha by remember {
        mutableFloatStateOf(0f)
    }

    var isIncrease by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(20)

            if (isIncrease) {
                alpha += 0.05f
                if (alpha >= 1f) isIncrease = false
            } else {
                alpha -= 0.05f
                if (alpha <= 0f) isIncrease = true
            }

            if (alpha < 0f) alpha = 0f
            if (alpha > 1f) alpha = 1f
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.fillMaxSize(),
            startAngle = 270f,
            strokeWidth = ScreenSize.screenHeightDp.toDpSize(3),
            trackColor = Color.White,
            indicatorColor = MaterialTheme.colors.primary.copy(alpha = alpha)
        )

        TimerDoneBody()

        SwipeDismissBox(
            modifier = modifier
                .align(Alignment.CenterStart),
            onDismissed = {
                VibrateUtil.cancel()
                navigateUp()
            }
        )
    }
}

@Composable
fun TimerDoneBody(
    modifier: Modifier = Modifier,
) {
    val btnSize = ScreenSize.screenHeightDp.toDpSize(22)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = ScreenSize.screenHeightDp.toDpSize(6),
                vertical = ScreenSize.screenHeightDp.toDpSize(7)
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(ScreenSize.screenHeightDp.toDpSize(14)),
            painter = painterResource(R.drawable.alarm),
            contentDescription = stringResource(id = R.string.timer_done_icon),
            tint = Color.White,
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(3))
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ScreenSize.screenHeightDp.toDpSize(5)),
                text = ColorCombineTextUtil.makeTimerDoneText("끓이기", LocalContext.current),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = ScreenSize.screenHeightDp.toSpSize(10),
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "06:00",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = ScreenSize.screenHeightDp.toSpSize(7),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        TimerDoneBtnRow(
            btnSize = btnSize,
        )
    }
}

@Composable
fun TimerDoneBtnRow(
    modifier: Modifier = Modifier,
    btnSize: Dp,
) {
    val context = LocalContext.current

    Button(
        modifier = modifier
            .size(btnSize),
        onClick = {
            VibrateUtil.cancel()
            (context as? Activity)?.finish()
        }
    ) {
        Icon(
            modifier = Modifier
                .background(MaterialTheme.colors.secondaryVariant)
                .padding(ScreenSize.screenHeightDp.toDpSize(6)),
            painter = painterResource(id = R.drawable.done),
            contentDescription = stringResource(id = R.string.btn_timer_done),
            tint = Color.White
        )
    }
}