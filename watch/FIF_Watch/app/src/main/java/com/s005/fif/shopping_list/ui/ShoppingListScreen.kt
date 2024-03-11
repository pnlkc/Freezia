package com.s005.fif.shopping_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Checkbox
import androidx.wear.compose.material.CheckboxDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipColors
import androidx.wear.compose.material.ToggleChipDefaults
import com.s005.fif.R
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(ScreenSize.screenHeightDp.toDpSize(2))
    ) {
        ShoppingListBody()
    }
}

@Composable
fun ShoppingListBody(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .padding(top = ScreenSize.screenHeightDp.toDpSize(5))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = ScreenSize.screenHeightDp.toDpSize(5)),
            text = stringResource(id = R.string.shopping_list),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(10f),
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(20) {
                ToggleChip(
                    modifier = Modifier
                        .fillMaxWidth(),
                    checked = true,
                    onCheckedChange = { },
                    label = {
                        Text(text = "토마토")
                    },
                    toggleControl = {
                        Checkbox(
                            modifier = Modifier,
                            checked = false,
                            colors = CheckboxDefaults.colors(
                                checkedBoxColor = MaterialTheme.colors.primary,
                                uncheckedBoxColor = MaterialTheme.colors.primary
                            )
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
        }
    }
}