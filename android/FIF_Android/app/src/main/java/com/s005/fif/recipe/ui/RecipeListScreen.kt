package com.s005.fif.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.s005.fif.main.ui.MainBody
import com.s005.fif.main.ui.MainTopBar
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.select.UserSelectItem
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
       RecipeListBody()
    }
}

@Composable
fun RecipeListBody(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        RecipeListTagList()
    }
}

@Composable
fun RecipeListTagList(
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = listOf<Pair<String, Boolean>>(
                "한식" to true,
                "중식" to false,
                "양식" to false,
                "일식" to false,
                "면 요리" to false,
                "국물 요리" to false,
                "볶음 요리" to false,
                "찜 요리" to false,
                "튀김 요리" to false
            ),
            key = { _, item ->
                item
            }
        ) { _, item ->
            RecipeListTagItem(
                item = item
            )
        }
    }
}

@Composable
fun RecipeListTagItem(
    modifier: Modifier = Modifier,
    item: Pair<String, Boolean>,
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .clickable {  }
            .background(if (item.second) MaterialTheme.colorScheme.primary else Color.White)
            .padding(vertical = 5.dp, horizontal = 10.dp),
        text = item.first,
        style = Typography.bodyMedium,
        color = if (item.second) MaterialTheme.colorScheme.onPrimary else Color.Black,
        maxLines = 1
    )
}