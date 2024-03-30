package com.s005.fif.recipe.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil.heightDp
import com.s005.fif.utils.ScreenSizeUtil.statusBarHeightDp
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.ScreenSizeUtil.widthDp
import kotlinx.coroutines.launch

enum class RecipeDetailType {
    IngredientList, MyFoodHistory
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeId: Int,
    navigateUp: () -> Unit,
    navigateToRecipeStep: (Int) -> Unit,
) {
    val recipe = recipeViewModel.getRecipe(recipeId = recipeId)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        recipeViewModel.getCompleteRecipeRecord(recipeId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxHeight(0.4f),
                model = recipe?.imgUrl ?: "",
                contentDescription = stringResource(id = R.string.description_recipe_img),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.2f), BlendMode.ColorBurn),
                loading = placeholder(R.drawable.close),
                failure = placeholder(R.drawable.close)
            )

            RecipeDetailBody(
                modifier = Modifier,
                recipeViewModel = recipeViewModel,
                navigateUp = navigateUp,
                navigateToRecipeStep = {
                    coroutineScope.launch {
                        recipeViewModel.getRecipeStep(recipeId)
                        navigateToRecipeStep(recipeId)
                    }
                },
                recipe = recipe
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(colorScheme.background)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun RecipeDetailTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    isExpanded: Boolean,
    recipe: RecipeListItem?,
    onRecipeSaveBtnClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { navigateUp() },
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back),
            tint = Color.White
        )

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = recipe?.name ?: "요리 이름",
                style = Typography.bodyLarge,
                color = Color.White
            )
        }

        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { onRecipeSaveBtnClicked() },
            painter = if (recipe!!.saveYn) painterResource(id = R.drawable.bookmark_fill) else painterResource(id = R.drawable.bookmark),
            contentDescription = stringResource(id = R.string.description_btn_bookmark),
            tint = if (recipe.saveYn) colorScheme.primary else Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailBody(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    navigateUp: () -> Unit,
    navigateToRecipeStep: () -> Unit,
    recipe: RecipeListItem?,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        isExpanded = when (scaffoldState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> {
                true
            }

            SheetValue.PartiallyExpanded -> {
                false
            }

            SheetValue.Hidden -> {
                scaffoldState.bottomSheetState.partialExpand()
                false
            }
        }
    }

    BottomSheetScaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            RecipeDetailTopBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                navigateUp = { navigateUp() },
                isExpanded = isExpanded,
                recipe = recipe,
                onRecipeSaveBtnClicked = {
                    coroutineScope.launch {
                        recipeViewModel.saveRecipe(recipe!!.recipeId)
                    }
                }
            )
        },
        sheetContent = {
            RecipeDetailBottomSheetColumn(
                modifier = Modifier
                    .height((heightDp - statusBarHeightDp - 45).dp),
                recipeViewModel = recipeViewModel,
                navigateToRecipeStep = navigateToRecipeStep,
                recipe = recipe
            )
        },
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = heightDp.toDpSize(70),
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.Transparent,
        sheetContainerColor = colorScheme.background,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier.size(heightDp.toDpSize(18))
            )

            AnimatedVisibility(
                visible = !isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        text = recipe?.name ?: "요리 이름",
                        style = Typography.titleMedium,
                        color = Color.White
                    )

                    Box(
                        modifier = Modifier.size(heightDp.toDpSize(1))
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        itemsIndexed(
                            items = recipe?.recipeTypes?.split(",") ?: listOf(),
                            key = { _, item ->
                                item
                            }
                        ) { _, item ->
                            RecipeDetailFoodTag(
                                modifier = Modifier,
                                text = item
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeDetailFoodTag(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFF565656).copy(alpha = 0.7f))
            .padding(vertical = 5.dp, horizontal = 10.dp),
        text = "#$text",
        style = Typography.bodyMedium,
        color = Color.White,
        maxLines = 1
    )
}

@Composable
fun RecipeDetailBottomSheetColumn(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    navigateToRecipeStep: () -> Unit,
    recipe: RecipeListItem?,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        RecipeDetailInfoRow(
            time = recipe?.cookTime ?: 0,
            calorie = recipe?.calorie ?: 0
        )

        RecipeDetailBtn(
            navigateToRecipeStep = navigateToRecipeStep
        )

        RecipeDetailPager(
            recipe = recipe,
            recipeViewModel = recipeViewModel
        )
    }
}

@Composable
fun RecipeDetailInfoRow(
    modifier: Modifier = Modifier,
    time: Int,
    calorie: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        RecipeDetailInfoItemColumn(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_cook_time),
            body = "${time}m",
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            thickness = 1.dp,
            color = colorScheme.onSecondary.copy(alpha = 0.2f)
        )

        RecipeDetailInfoItemColumn(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_calorie),
            body = "${calorie}kcal"
        )
    }
}

@Composable
fun RecipeDetailInfoItemColumn(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Typography.bodySmall,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = body,
            style = Typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun RecipeDetailBtn(
    modifier: Modifier = Modifier,
    navigateToRecipeStep: () -> Unit,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = widthDp.toDpSize(20))
            .clip(RoundedCornerShape(50.dp))
            .background(colorScheme.onSecondary.copy(alpha = 0.2f))
            .clickable { navigateToRecipeStep() }
            .padding(vertical = 10.dp),
        text = stringResource(id = R.string.text_go_to_recipe_step),
        style = Typography.bodySmall,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeDetailPager(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipe: RecipeListItem?,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()
    var selected by remember {
        mutableStateOf(RecipeDetailType.IngredientList)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selected =
                if (page == 0) RecipeDetailType.IngredientList else RecipeDetailType.MyFoodHistory
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalDivider(
            thickness = 5.dp,
            color = colorScheme.onSecondary.copy(alpha = 0.2f)
        )

        RecipeDetailTitleRow(
            selected = selected,
            ingredientListClicked = {
                selected = RecipeDetailType.IngredientList

                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            myFoodHistoryClicked = {
                selected = RecipeDetailType.MyFoodHistory

                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        )

        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            state = pagerState,
            pageSpacing = 20.dp,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    IngredientListPage(
                        recipe = recipe,
                        recipeViewModel = recipeViewModel
                    )
                }

                1 -> {
                    MyFoodHistoryPage(
                        recipeViewModel = recipeViewModel,
                        recipeId = recipe!!.recipeId
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeDetailTitleRow(
    modifier: Modifier = Modifier,
    selected: RecipeDetailType,
    ingredientListClicked: () -> Unit,
    myFoodHistoryClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        RecipeDetailTitleItem(
            modifier = Modifier
                .weight(1f),
            text = stringResource(id = R.string.text_need_ingredient),
            selected = selected == RecipeDetailType.IngredientList,
            onClick = ingredientListClicked
        )

        RecipeDetailTitleItem(
            modifier = Modifier
                .weight(1f),
            text = stringResource(id = R.string.text_my_food_history),
            selected = selected == RecipeDetailType.MyFoodHistory,
            onClick = myFoodHistoryClicked
        )
    }
}

@Composable
fun RecipeDetailTitleItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            text = text,
            style = Typography.titleSmall,
            color = if (selected) colorScheme.primary else Color.Black,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(if (selected) colorScheme.primary else Color.White)
        )
    }
}