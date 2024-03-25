package com.s005.fif.timer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.timer.entity.TimerInfo
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import com.s005.fif.utils.TimeUtil.toTime

@Composable
fun TimerListScreen(
    modifier: Modifier = Modifier,
    navigateToTimerDetail: (Int) -> Unit,
    viewModel: TimerViewModel = hiltViewModel(),
) {
    val scalingLazyListState = rememberScalingLazyListState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        TimerListBody(
            modifier = modifier,
            scalingLazyListState = scalingLazyListState,
            navigateToTimerDetail = navigateToTimerDetail,
            timerList = { viewModel.timerList },
            timerBtnClicked = { isStart, timerInfo ->
                viewModel.timerBtnClicked(isStart, context, timerInfo)
            }
        )

        PositionIndicator(
            scalingLazyListState = scalingLazyListState,
        )
    }
}

@Composable
fun TimerListBody(
    modifier: Modifier = Modifier,
    scalingLazyListState: ScalingLazyListState,
    navigateToTimerDetail: (Int) -> Unit,
    timerList: () -> List<TimerInfo>,
    timerBtnClicked: (Boolean, TimerInfo) -> Unit
) {
    Column(
        modifier = modifier
            .padding(ScreenSize.screenHeightDp.toDpSize(2))
            .padding(top = ScreenSize.screenHeightDp.toDpSize(5))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = ScreenSize.screenHeightDp.toDpSize(2)),
            text = stringResource(id = R.string.timer_list),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(8),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = scalingLazyListState
        ) {
            itemsIndexed(
                items = timerList(),
                key =  { _, item ->
                    item.timerId
                }
            ) { idx, item ->
                TimerListChip(
                    navigateToTimerDetail = {
                        navigateToTimerDetail(idx)
                    },
                    item = item,
                    timerBtnClicked = timerBtnClicked
                )
            }
        }
    }
}

@Composable
fun TimerListChip(
    modifier: Modifier = Modifier,
    navigateToTimerDetail: () -> Unit,
    item: TimerInfo,
    timerBtnClicked: (Boolean, TimerInfo) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ScreenSize.screenHeightDp.toDpSize(30))
            .clip(RoundedCornerShape(ScreenSize.screenHeightDp.toDpSize(30)))
            .background(MaterialTheme.colors.onError)
            .clickable { navigateToTimerDetail() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = ScreenSize.screenHeightDp.toDpSize(3))
                .padding(
                    start = ScreenSize.screenHeightDp.toDpSize(3),
                    end = ScreenSize.screenHeightDp.toDpSize(2)
                )
        ) {
            TimerBtn(
                isStart = item.isStart,
                startBtnClicked = {
                    timerBtnClicked(!item.isStart, item)
                },
                size = ScreenSize.screenHeightDp.toDpSize(24)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = ScreenSize.screenHeightDp.toDpSize(2)),
                text = String.format("%02d:%02d", item.leftTime.toTime().m, item.leftTime.toTime().s),
                fontSize = ScreenSize.screenHeightDp.toSpSize(12),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = ScreenSize.screenHeightDp.toDpSize(1),
                        bottom = ScreenSize.screenHeightDp.toDpSize(2)
                    ),
                text = item.title,
                fontSize = ScreenSize.screenHeightDp.toSpSize(5),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black.copy(0.5f)
            )
        }

        Icon(
            modifier = Modifier
                .size(ScreenSize.screenHeightDp.toDpSize(10))
                .clip(CircleShape)
                .padding(ScreenSize.screenHeightDp.toDpSize(2)),
            painter = painterResource(id = R.drawable.arrow_forward),
            contentDescription = stringResource(id = R.string.timer_list_to_detail_icon),
            tint = Color.Black
        )
    }
}

@Composable
fun TimerBtn(
    modifier: Modifier = Modifier,
    isStart: Boolean,
    startBtnClicked: () -> Unit,
    size: Dp,
) {
    Image(
        modifier = modifier
            .clip(CircleShape)
            .size(size)
            .clickable { startBtnClicked() }
            .background(if (isStart) Color.White else MaterialTheme.colors.primary)
            .padding(size / 10),
        painter = painterResource(id = if (isStart) R.drawable.pause else R.drawable.play),
        contentDescription = stringResource(id = R.string.btn_timer_play),
        colorFilter = ColorFilter.tint(if (isStart) MaterialTheme.colors.primary else Color.White),
    )
}