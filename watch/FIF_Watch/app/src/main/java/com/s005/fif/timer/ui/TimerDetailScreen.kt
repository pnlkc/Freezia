package com.s005.fif.timer.ui

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.timer.entity.TimerInfo
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerDetailScreen(
    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel,
    navigateUp: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    idx: Int
) {
    Log.d("로그", " - TimerDetailScreen() 호출됨 / ${timerViewModel.timerList}")

    val context = LocalContext.current
    val maxPages = timerViewModel.timerList.size
    var selectedPage by remember { mutableIntStateOf(idx) }
    val pagerState = rememberPagerState(
        initialPage = selectedPage,
        pageCount = { maxPages }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedPage = page
        }
    }

    HorizontalPager(
        modifier = modifier
            .fillMaxSize(),
        state = pagerState
    ) { page ->
        TimerDetailBody(
            goTimerBack = {
                if (selectedPage > 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(--selectedPage)
                    }
                }
            },
            goTimerForward = {
                if (selectedPage < maxPages) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(++selectedPage)
                    }
                }
            },
            navigateUp = navigateUp,
            navigateToRecipeDetail = navigateToRecipeDetail,
            item = { timerViewModel.timerList[page] },
            timerClicked = { isStart, timerInfo ->
                timerViewModel.timerBtnClicked(isStart, context, timerInfo)
            }
        )
    }
}

@Composable
fun TimerDetailBody(
    modifier: Modifier = Modifier,
    goTimerBack: () -> Unit,
    goTimerForward: () -> Unit,
    navigateUp: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    item: () -> TimerInfo,
    timerClicked: (Boolean, TimerInfo) -> Unit
) {
    val timerInfo = item()

    val progressAnimDuration = 1000
    val progressAnimation by animateFloatAsState(
        targetValue = (timerInfo.initTime - timerInfo.leftTime).toFloat() / (timerInfo.initTime - 1),
        animationSpec = tween(durationMillis = progressAnimDuration, easing = LinearEasing),
        label = "",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            progress = progressAnimation,
            modifier = Modifier.fillMaxSize(),
            startAngle = 270f,
            strokeWidth = ScreenSize.screenHeightDp.toDpSize(3),
            trackColor = Color.White
        )

        TimerDetailPage(
            goTimerBack = goTimerBack,
            goTimerForward = goTimerForward,
            navigateUp = navigateUp,
            navigateToRecipeDetail = navigateToRecipeDetail,
            item = item,
            timerClicked = {
                timerClicked(!item().isStart, item())
            }
        )

        SwipeDismissBox(
            modifier = modifier
                .align(Alignment.CenterStart),
            onDismissed = navigateUp
        )
    }
}

@Composable
fun SwipeDismissBox(
    modifier: Modifier = Modifier,
    onDismissed: () -> Unit,
) {
    SwipeToDismissBox(
        modifier = modifier
            .size(ScreenSize.screenHeightDp.toDpSize(5)),
        onDismissed = { onDismissed() },
        contentScrimColor = Color.Transparent,
        backgroundScrimColor = Color.Transparent
    ) { _ ->
    }
}

@Composable
fun TimerDetailPage(
    modifier: Modifier = Modifier,
    goTimerBack: () -> Unit,
    goTimerForward: () -> Unit,
    navigateUp: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    item: () -> TimerInfo,
    timerClicked: () -> Unit,
) {
    val btnSize = ScreenSize.screenHeightDp.toDpSize(22)
    val arrowBtnSize = ScreenSize.screenHeightDp.toDpSize(10)
    val btnBottomPadding = ScreenSize.screenHeightDp.toDpSize(15)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = ScreenSize.screenWidthDp.toDpSize(30))
                .padding(top = ScreenSize.screenWidthDp.toDpSize(10)),
            text = item().title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(7),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = ScreenSize.screenHeightDp.toDpSize(5))
                .padding(bottom = ScreenSize.screenHeightDp.toDpSize(12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(arrowBtnSize)
                    .clip(CircleShape)
                    .clickable { goTimerBack() }
                    .padding(ScreenSize.screenHeightDp.toDpSize(2)),
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = stringResource(id = R.string.btn_timer_back),
                tint = Color.White
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(3))
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = String.format("%02d:%02d", item().leftTime / 60, item().leftTime % 60),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = ScreenSize.screenHeightDp.toSpSize(15),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = String.format("%02d:%02d", item().initTime / 60, item().initTime % 60),
                    textAlign = TextAlign.Center,
                    fontSize = ScreenSize.screenHeightDp.toSpSize(7),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                modifier = Modifier
                    .size(arrowBtnSize)
                    .clip(CircleShape)
                    .clickable { goTimerForward() }
                    .padding(ScreenSize.screenHeightDp.toDpSize(2)),
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = stringResource(id = R.string.btn_timer_forward),
                tint = Color.White
            )
        }

        TimerDetailBtnRow(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = ScreenSize.screenWidthDp.toDpSize(9),
                    vertical = ScreenSize.screenWidthDp.toDpSize(7),
                ),
            btnSize = btnSize,
            btnBottomPadding = btnBottomPadding,
            navigateUp = navigateUp,
            navigateToRecipeDetail = navigateToRecipeDetail,
            isStart = item().isStart,
            startBtnClicked = timerClicked
        )
    }
}

@Composable
fun TimerDetailBtnRow(
    modifier: Modifier = Modifier,
    btnBottomPadding: Dp,
    btnSize: Dp,
    navigateUp: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    isStart: Boolean,
    startBtnClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Button(
            modifier = Modifier
                .padding(bottom = btnBottomPadding)
                .size(btnSize)
                .align(Alignment.TopStart),
            onClick = { },
        ) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondaryVariant)
                    .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                painter = painterResource(id = R.drawable.replay),
                contentDescription = stringResource(id = R.string.btn_timer_reset),
                tint = Color.White
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(1.5f))
        ) {
            Button(
                modifier = Modifier
                    .size(btnSize),
                onClick = { navigateUp() }
            ) {
                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondaryVariant)
                        .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = stringResource(id = R.string.btn_timer_list),
                    tint = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .size(btnSize),
                onClick = { navigateToRecipeDetail() }
            ) {
                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondaryVariant)
                        .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                    painter = painterResource(id = R.drawable.food),
                    contentDescription = stringResource(id = R.string.btn_timer_to_recipe),
                    tint = Color.White
                )
            }
        }

        TimerBtn(
            modifier = Modifier
                .padding(bottom = btnBottomPadding)
                .align(Alignment.TopEnd),
            isStart = isStart,
            startBtnClicked = startBtnClicked,
            size = btnSize
        )
    }
}