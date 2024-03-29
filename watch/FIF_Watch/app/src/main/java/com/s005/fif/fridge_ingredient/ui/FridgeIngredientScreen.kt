package com.s005.fif.fridge_ingredient.ui

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Checkbox
import androidx.wear.compose.material.CheckboxDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.fridge_ingredient.dto.FridgeIngredientItem
import com.s005.fif.shopping_list.dto.ShoppingListItem
import com.s005.fif.timer.ui.TimerBtn
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import com.s005.fif.utils.TimeUtil.toTime

@Composable
fun FridgeIngredientScreen(
    modifier: Modifier = Modifier,
    fridgeIngredientViewModel: FridgeIngredientViewModel,
) {
    val scalingLazyListState = rememberScalingLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        FridgeIngredientBody(
            modifier = modifier,
            scalingLazyListState = scalingLazyListState,
            fridgeIngredientList = fridgeIngredientViewModel.fridgeIngredientList.toList()
        )

        PositionIndicator(
            scalingLazyListState = scalingLazyListState,
        )
    }
}

@Composable
fun FridgeIngredientBody(
    modifier: Modifier = Modifier,
    scalingLazyListState: ScalingLazyListState,
    fridgeIngredientList: List<FridgeIngredientItem>
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
            text = stringResource(id = R.string.fridge_ingredient_list),
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
            state = scalingLazyListState,
            anchorType = ScalingLazyListAnchorType.ItemStart
        ) {
            itemsIndexed(
                items = fridgeIngredientList,
                key = { _, item ->
                    item.fridgeIngredientId
                }
            ) { _, item ->
                FridgeIngredientChip(
                    modifier = Modifier,
                    item = item,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FridgeIngredientChip(
    modifier: Modifier = Modifier,
    item: FridgeIngredientItem,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ScreenSize.screenHeightDp.toDpSize(25))
            .clip(RoundedCornerShape(ScreenSize.screenHeightDp.toDpSize(30)))
            .background(MaterialTheme.colors.onError),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(5))
    ) {
        GlideImage(
            modifier = modifier
                .padding(start = ScreenSize.screenHeightDp.toDpSize(2))
                .size(ScreenSize.screenHeightDp.toDpSize(20))
                .clip(CircleShape),
            model = item.imgUrl,
            contentDescription = stringResource(id = R.string.ingredient_img_desc),
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.basic_ingredient),
            failure = placeholder(R.drawable.basic_ingredient)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = ScreenSize.screenHeightDp.toDpSize(2)),
            text = item.name,
            fontSize = ScreenSize.screenHeightDp.toSpSize(8),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
        )
    }
}