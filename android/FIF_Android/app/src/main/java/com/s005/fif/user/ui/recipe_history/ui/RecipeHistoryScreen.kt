package com.s005.fif.user.ui.recipe_history.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.main.dto.RecommendRecipeListItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.RecipeHistoryItem
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.profile.UserProfileTopBar
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType.CompletedFood
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType.SavedRecipe
import com.s005.fif.utils.ScreenSizeUtil
import kotlinx.coroutines.launch


enum class RecipeHistoryType {
    SavedRecipe, CompletedFood
}

@Composable
fun RecipeHistoryScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    recipeHistoryViewModel: RecipeHistoryViewModel,
    mode: RecipeHistoryType,
    navigateUp: () -> Unit,
    navigateToRecipeDetail: (Int) -> Unit
) {
    LaunchedEffect(key1 = true) {
        recipeHistoryViewModel.getSavedRecipeList()
        recipeHistoryViewModel.getCompletedRecipeList()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp,
            memberInfo = userViewModel.memberInfo,
            navigateToRecipeHistory = {  }
        )

        SavedRecipeBody(
            mode = mode,
            savedRecipeList = recipeHistoryViewModel.savedRecipeList,
            completedRecipeList = recipeHistoryViewModel.completedRecipeList,
            navigateToRecipeDetail = navigateToRecipeDetail
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedRecipeBody(
    modifier: Modifier = Modifier,
    mode: RecipeHistoryType,
    savedRecipeList: List<RecipeHistoryItem>,
    completedRecipeList: List<RecipeHistoryItem>,
    navigateToRecipeDetail: (Int) -> Unit
) {
    var selected by remember { mutableStateOf(mode) }
    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = if (mode == SavedRecipe) 0 else 1
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selected = if (page == 0) SavedRecipe else CompletedFood
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp),

    ) {
        SavedRecipeTitleRow(
            selected = selected,
            savedRecipeClicked = {
                selected = SavedRecipe

                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            completedFoodClicked = {
                selected = CompletedFood

                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
            savedRecipeListSize = savedRecipeList.size,
            completedRecipeListSize = completedRecipeList.size
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            state = pagerState,
            userScrollEnabled = true,
            pageSpacing = 20.dp
        ) { page ->
            when (page) {
                0 -> {
                    // TODO. 실제 리스트로 변경 필요
                    RecipeHistoryPagerItem(
                        list = savedRecipeList,
                        onItemClicked = navigateToRecipeDetail
                    )
                }
                1 -> {
                    // TODO. 실제 리스트로 변경 필요
                    RecipeHistoryPagerItem(
                        list = completedRecipeList,
                        onItemClicked = navigateToRecipeDetail
                    )
                }
            }
        }
    }
}

@Composable
fun SavedRecipeTitleRow(
    modifier: Modifier = Modifier,
    selected: RecipeHistoryType,
    savedRecipeClicked: () -> Unit,
    completedFoodClicked: () -> Unit,
    savedRecipeListSize: Int,
    completedRecipeListSize: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
    ) {
        SavedRecipeTitleItem(
            modifier = Modifier
                .weight(1f),
            icon = painterResource(id = R.drawable.bookmark_fill),
            text = stringResource(id = R.string.text_saved_recipe) + " (${savedRecipeListSize})",
            iconColor = if (selected == SavedRecipe) Color.White else colorScheme.primary,
            textColor = if (selected == SavedRecipe) Color.White else Color.Black,
            backgroundColor = if (selected == SavedRecipe) colorScheme.primary else Color.White,
            onClick = savedRecipeClicked
        )

        SavedRecipeTitleItem(
            modifier = Modifier
                .weight(1f),
            icon = painterResource(id = R.drawable.complete),
            text = stringResource(id = R.string.text_completed_food)+ " (${completedRecipeListSize})",
            iconColor = if (selected == SavedRecipe) colorScheme.primary else Color.White,
            textColor = if (selected == SavedRecipe) Color.Black else Color.White,
            backgroundColor = if (selected == SavedRecipe) Color.White else colorScheme.primary,
            onClick = completedFoodClicked
        )
    }
}

@Composable
fun SavedRecipeTitleItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String,
    iconColor: Color,
    textColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 5.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(20.dp),
            painter = icon,
            contentDescription = stringResource(id = R.string.description_btn_go_back),
            tint = iconColor
        )

        Text(
            text = text,
            style = Typography.bodyMedium,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RecipeHistoryPagerItem(
    modifier: Modifier = Modifier,
    list: List<RecipeHistoryItem>,
    onItemClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(
            items = list,
            key = { _, item ->
                item.imgUrl
            }
        ) { _, item ->
            RecipeHistoryLazyVerticalGridItem(
                item = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun RecipeHistoryLazyVerticalGridItem(
    modifier: Modifier = Modifier,
    item: RecipeHistoryItem,
    onItemClicked: (Int) -> Unit
) {
    RecipeHistoryLazyVerticalGridItemBox(
        modifier = modifier,
        recipeId = item.recipeId,
        imgUrl = item.imgUrl,
        name = item.name,
        cookTime = item.cookTime,
        onItemClicked = onItemClicked
    )
}

@Composable
fun RecipeHistoryLazyVerticalGridItem(
    modifier: Modifier = Modifier,
    item: RecipeListItem,
    onItemClicked: (Int) -> Unit
) {
    RecipeHistoryLazyVerticalGridItemBox(
        modifier = modifier,
        recipeId =item.recipeId,
        imgUrl = item.imgUrl,
        name = item.name,
        cookTime = item.cookTime,
        onItemClicked = onItemClicked
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeHistoryLazyVerticalGridItemBox(
    modifier: Modifier = Modifier,
    recipeId: Int,
    imgUrl: String,
    name: String,
    cookTime: Int,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((ScreenSizeUtil.heightDp / 5).dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onItemClicked(recipeId) }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imgUrl,
            contentDescription = stringResource(id = R.string.description_recipe_img),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.1f), BlendMode.ColorBurn)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.Bottom
            )
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(15.dp),
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = stringResource(id = R.string.description_time_icon),
                    tint = Color.White
                )

                Text(
                    text = "$cookTime min",
                    style = Typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 2.dp),
                text = name,
                style = Typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}