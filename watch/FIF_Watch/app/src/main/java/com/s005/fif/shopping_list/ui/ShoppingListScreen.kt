package com.s005.fif.shopping_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Checkbox
import androidx.wear.compose.material.CheckboxDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.s005.fif.R
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
) {
    val scalingLazyListState = rememberScalingLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ShoppingListBody(
            modifier = modifier,
            scalingLazyListState = scalingLazyListState
        )

        PositionIndicator(
            scalingLazyListState = scalingLazyListState,
        )
    }
}

@Composable
fun ShoppingListBody(
    modifier: Modifier = Modifier,
    scalingLazyListState: ScalingLazyListState,
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
            text = stringResource(id = R.string.shopping_list),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(10),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        // TODO. 임시 체크박스 변경용 변수
        val isChecked = remember { mutableStateOf(true) }

        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = scalingLazyListState
        ) {
            items(20) {
                ShoppingListChip(
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ShoppingListChip(
    modifier: Modifier = Modifier,
) {
    var isChecked by remember {
        mutableStateOf(true)
    }

    ToggleChip(
        modifier = Modifier
            .fillMaxWidth(),
        checked = isChecked,
        onCheckedChange = {
            isChecked = it
        },
        label = {
            Text(
                text = "토마토",
                textDecoration = if (isChecked) TextDecoration.LineThrough else null
            )
        },
        toggleControl = {
            Checkbox(
                modifier = Modifier,
                checked = isChecked,
                colors = CheckboxDefaults.colors(
                    checkedBoxColor = MaterialTheme.colors.primary,
                    uncheckedBoxColor = Color.White
                ),
            )
        },
        colors = ToggleChipDefaults.toggleChipColors(
            checkedStartBackgroundColor = MaterialTheme.colors.primaryVariant,
            checkedEndBackgroundColor = MaterialTheme.colors.primaryVariant,
            checkedContentColor = Color.White,
            uncheckedStartBackgroundColor = MaterialTheme.colors.primaryVariant,
            uncheckedEndBackgroundColor = MaterialTheme.colors.primaryVariant,
            uncheckedContentColor = Color.White
        )
    )
}