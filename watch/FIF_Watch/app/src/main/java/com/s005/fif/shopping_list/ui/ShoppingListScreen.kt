package com.s005.fif.shopping_list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Checkbox
import androidx.wear.compose.material.CheckboxDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.s005.fif.R
import com.s005.fif.shopping_list.dto.ShoppingListItem
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import kotlinx.coroutines.launch

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    shoppingListViewModel: ShoppingListViewModel,
) {
    val scalingLazyListState = rememberScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ShoppingListBody(
            modifier = modifier,
            scalingLazyListState = scalingLazyListState,
            itemClicked = { idx, id, isChecked ->
                coroutineScope.launch {
                    shoppingListViewModel.checkShoppingListItem(idx, id, isChecked)
                }
            },
            shoppingList = { shoppingListViewModel.shoppingList.value }
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
    itemClicked: (Int, Int, Boolean) -> Unit,
    shoppingList: () -> List<ShoppingListItem>
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

        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = scalingLazyListState,
            anchorType = ScalingLazyListAnchorType.ItemStart
        ) {
            itemsIndexed(
                items = shoppingList(),
                key = { idx, item ->
                    "$idx ${item.shoppingListId} ${item.checkYn}"
                }
            ) { idx, item ->
                ShoppingListChip(
                    modifier = Modifier,
                    item = item,
                    idx = idx,
                    itemClicked = itemClicked
                )
            }
        }
    }
}

@Composable
fun ShoppingListChip(
    modifier: Modifier = Modifier,
    item: ShoppingListItem,
    idx: Int,
    itemClicked: (Int, Int, Boolean) -> Unit
) {
    ToggleChip(
        modifier = modifier
            .fillMaxWidth(),
        checked = item.checkYn,
        onCheckedChange = {
            itemClicked(idx, item.shoppingListId, it)
        },
        label = {
            Text(
                text = item.name,
                textDecoration = if (item.checkYn) TextDecoration.LineThrough else null
            )
        },
        toggleControl = {
            Checkbox(
                modifier = Modifier,
                checked = item.checkYn,
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