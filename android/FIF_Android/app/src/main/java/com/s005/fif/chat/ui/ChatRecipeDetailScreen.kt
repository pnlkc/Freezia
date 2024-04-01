package com.s005.fif.chat.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.core.text.isDigitsOnly
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.chat.data.ChatResultObject
import com.s005.fif.chat.dto.ChatResultIngredient
import com.s005.fif.chat.dto.ChatResultRecipeListItem
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.detail.IngredientListHeader
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.GCDUtil
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatRecipeDetailScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    recipeViewModel: RecipeViewModel,
    recipeIdx: Int,
    navigateUp: () -> Unit,
    navigateToChatRecipeStep: (Int) -> Unit
) {
    val recipe = chatViewModel.recipeList[recipeIdx]

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxHeight(0.4f),
                model = ChatResultObject.defaultRecipeImg,
                contentDescription = stringResource(id = R.string.description_recipe_img),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.2f), BlendMode.ColorBurn),
                loading = placeholder(R.drawable.close),
                failure = placeholder(R.drawable.close)
            )

            ChatRecipeDetailBody(
                modifier = Modifier,
                navigateUp = navigateUp,
                recipe = recipe,
                servings = recipeViewModel.servings,
                onServingsChanged = { isAdd ->
                    recipeViewModel.changeServings(isAdd)
                },
                navigateToChatRecipeStep = {
                    navigateToChatRecipeStep(recipeIdx)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ChatRecipeDetailTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    isExpanded: Boolean,
    recipe: ChatResultRecipeListItem,
) {
    Box(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { navigateUp() }
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back),
            tint = Color.White
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center),
            visible = isExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = recipe.name,
                style = Typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRecipeDetailBody(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    recipe: ChatResultRecipeListItem,
    servings: Int,
    onServingsChanged: (Boolean) -> Unit,
    navigateToChatRecipeStep: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    var isExpanded by remember {
        mutableStateOf(false)
    }

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
            ChatRecipeDetailTopBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                navigateUp = { navigateUp() },
                isExpanded = isExpanded,
                recipe = recipe
            )
        },
        sheetContent = {
            ChatRecipeDetailBottomSheetColumn(
                modifier = Modifier
                    .height((ScreenSizeUtil.heightDp - ScreenSizeUtil.statusBarHeightDp - 45).dp),
                navigateToChatRecipeStep = navigateToChatRecipeStep,
                recipe = recipe,
                servings = servings,
                onServingsChanged = onServingsChanged
            )
        },
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = ScreenSizeUtil.heightDp.toDpSize(70),
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = Color.Transparent,
        sheetContainerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier.size(ScreenSizeUtil.heightDp.toDpSize(18))
            )

            AnimatedVisibility(
                visible = !isExpanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column {
                    Text(
                        text = recipe.name,
                        style = Typography.titleMedium,
                        color = Color.White
                    )

                    Box(
                        modifier = Modifier.size(ScreenSizeUtil.heightDp.toDpSize(1))
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        itemsIndexed(
                            items = recipe.recipeType.split(","),
                            key = { _, item ->
                                item
                            }
                        ) { _, item ->
                            ChatRecipeDetailFoodTag(
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
fun ChatRecipeDetailFoodTag(
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
fun ChatRecipeDetailBottomSheetColumn(
    modifier: Modifier = Modifier,
    navigateToChatRecipeStep: () -> Unit,
    recipe: ChatResultRecipeListItem,
    servings: Int,
    onServingsChanged: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        ChatRecipeDetailInfoRow(
            time = recipe.cookTime,
            calorie = (recipe.calorie ?: recipe.carlorie) ?: "칼로리 정보 없음"
        )

        ChatRecipeDetailBtn(
            navigateToChatRecipeStep = navigateToChatRecipeStep
        )

        ChatRecipeDetailIngredientList(
            servings = servings,
            onServingsChanged = onServingsChanged,
            recipe = recipe
        )
    }
}

@Composable
fun ChatRecipeDetailInfoRow(
    modifier: Modifier = Modifier,
    time: String,
    calorie: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {
        ChatRecipeDetailInfoItemColumn(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_cook_time),
            body = time
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
        )

        ChatRecipeDetailInfoItemColumn(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_calorie),
            body = if (!calorie.contains("cal")) "$calorie kcal" else calorie
        )
    }
}

@Composable
fun ChatRecipeDetailInfoItemColumn(
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
fun ChatRecipeDetailBtn(
    modifier: Modifier = Modifier,
    navigateToChatRecipeStep: () -> Unit,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenSizeUtil.widthDp.toDpSize(20))
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f))
            .clickable { navigateToChatRecipeStep() }
            .padding(vertical = 10.dp),
        text = stringResource(id = R.string.text_go_to_recipe_step),
        style = Typography.bodySmall,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ChatRecipeDetailIngredientList(
    modifier: Modifier = Modifier,
    servings: Int,
    onServingsChanged: (Boolean) -> Unit,
    recipe: ChatResultRecipeListItem
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalDivider(
            thickness = 5.dp,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
        )

        Column(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                text = stringResource(id = R.string.text_need_ingredient),
                style = Typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            ChatRecipeIngredientList(
                servings = servings,
                onServingsChanged = onServingsChanged,
                recipe = recipe
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRecipeIngredientList(
    modifier: Modifier = Modifier,
    servings: Int,
    onServingsChanged: (Boolean) -> Unit,
    recipe: ChatResultRecipeListItem,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            IngredientListHeader(
                servings = servings,
                onBtnClicked = { isAdd ->
                    onServingsChanged(isAdd)
                }
            )
        }

        item {
            Box(modifier = Modifier.size(10.dp))
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.text_ingredient),
                style = Typography.bodyMedium
            )
        }

        itemsIndexed(
            items = recipe.ingredientList,
            key = { idx, item ->
                "${idx} ${item.name}"
            }
        ) { _, item ->
            ChatRecipeIngredientListItem(
                item = item,
                servings = servings
            )
        }

        item {
            Box(modifier = Modifier.size(10.dp))
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = stringResource(id = R.string.text_seasoning),
                style = Typography.bodyMedium
            )
        }

        itemsIndexed(
            items = recipe.seasoningList,
            key = { idx, item ->
                "${idx} ${item.name}"
            }
        ) { _, item ->
            ChatRecipeIngredientListItem(
                item = item,
                servings = servings
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatRecipeIngredientListItem(
    modifier: Modifier = Modifier,
    item: ChatResultIngredient,
    servings: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlideImage(
                    modifier = modifier
                        .clip(CircleShape)
                        .size(30.dp),
                    model = IngredientListData.getImageByName(item.name),
                    contentDescription = stringResource(id = R.string.description_ingredient_img),
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.drawable.basic_ingredient),
                    failure = placeholder(R.drawable.basic_ingredient)
                )

                Text(
                    text = item.name,
                    style = Typography.bodyMedium,
                )
            }

            Text(
                text = if (item.amounts.contains("/")) {
                    var (a, b) = item.amounts.split("/").map { it.toInt() }
                    a *= servings
                    val gcd = GCDUtil.gcd(a, b)
                    a /= gcd
                    b /= gcd

                    if (b == 1) {
                        "${a.toFloat()}${item.unit}"
                    } else {
                        "${a}/${b}${item.unit}"
                    }
                } else if (!item.amounts.isDigitsOnly()) {
                    item.amounts
                } else {
                    "${item.amounts.toFloat() * servings}${item.unit}"
                },
                style = Typography.bodyMedium,
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
        )
    }
}