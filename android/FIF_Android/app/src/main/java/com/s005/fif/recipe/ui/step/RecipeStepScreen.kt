package com.s005.fif.recipe.ui.step

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.dto.RecipeStepItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.detail.RecipeDetailInfoRow
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.ScreenSizeUtil.widthDp
import com.s005.fif.utils.TTSUtil
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeStepScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeId: Int,
    navigateUp: () -> Unit,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: (Int) -> Unit,
) {
    val recipe = recipeViewModel.getRecipe(recipeId = recipeId)!!
    val recipeStepList = recipeViewModel.recipeStepList
    val pagerState = rememberPagerState(pageCount = { recipeStepList.size + 1 })
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        TTSUtil.stop()
        navigateUp()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .background(colorScheme.background)
    ) {
        RecipeStepTopBar(
            navigateUp = navigateUp,
            title = recipe.name,
            isEnd = pagerState.currentPage == pagerState.pageCount - 1
        )

        RecipeStepBody(
            modifier = Modifier,
            recipe = recipe,
            recipeStepList = recipeStepList,
            pagerState = pagerState,
            navigateToRecipeList = navigateToRecipeList,
            navigateToRecipeComplete = {
                navigateToRecipeComplete(recipeId)
            },
            onRecipeSaveBtnClicked = {
                coroutineScope.launch {
                    recipeViewModel.saveRecipe(recipeId)
                }
            }
        )
    }
}

@Composable
fun RecipeStepTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    title: String,
    isEnd: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
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
            tint = Color.Black
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center),
            visible = !isEnd,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = title,
                style = Typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeStepBody(
    modifier: Modifier = Modifier,
    recipe: RecipeListItem,
    recipeStepList: List<RecipeStepItem>,
    pagerState: PagerState,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit,
    onRecipeSaveBtnClicked: () -> Unit,
) {
    val context = LocalContext.current
    val isEnd = pagerState.currentPage == pagerState.pageCount - 1
    var progress by remember {
        mutableFloatStateOf(0f)
    }
    val progressAnimDuration = 1000
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )

    val pageModifier = { page: Int ->
        Modifier
            .fillMaxWidth()
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                alpha = lerp(
                    start = 0.3f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                scaleY = lerp(
                    start = 0.9f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            progress = (page + 1) / (pagerState.pageCount - 1).toFloat()

            TTSUtil.stop()
        }
    }

    Column(
        modifier = modifier
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(25.dp)
                .fillMaxWidth()
        ) {
            this@Column.AnimatedVisibility(
                visible = !isEnd,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                RecipeStepProgressBar(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    progress = progressAnimation,
                    page = pagerState.currentPage + 1,
                    maxPage = pagerState.pageCount - 1
                )
            }

            this@Column.AnimatedVisibility(
                visible = isEnd,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.text_complete_recipe_title),
                    style = Typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        HorizontalPager(
            modifier = modifier
                .weight(1f),
            state = pagerState,
            pageSize = PageSize.Fixed(widthDp.toDpSize(80)),
            pageSpacing = 10.dp,
            contentPadding = PaddingValues(horizontal = widthDp.toDpSize(10))
        ) { page ->
            RecipeStepPage(
                modifier = pageModifier(page),
                page = page,
                isEnd = isEnd,
                recipe = recipe,
                recipeStep = recipeStepList[minOf(page, pagerState.pageCount - 2)],
                navigateToRecipeList = navigateToRecipeList,
                navigateToRecipeComplete = navigateToRecipeComplete,
                onRecipeSaveBtnClicked = onRecipeSaveBtnClicked
            )
        }

        Box(
            modifier = Modifier.size(60.dp)
        ) {
            this@Column.AnimatedVisibility(
                visible = !isEnd,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextToSpeechBtn(
                    onClick = {
                        TTSUtil.speak(context, recipeStepList[pagerState.currentPage].description)
                    }
                )
            }
        }
    }
}

@Composable
fun RecipeStepProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    page: Int,
    maxPage: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .clip(RoundedCornerShape(50.dp)),
            progress = { progress },
            trackColor = Color.White,
            color = colorScheme.primary
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            text = "${minOf(page, maxPage)}/${maxPage}",
            style = Typography.bodyMedium,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeStepPage(
    modifier: Modifier = Modifier,
    page: Int,
    isEnd: Boolean,
    recipe: RecipeListItem,
    recipeStep: RecipeStepItem,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit,
    onRecipeSaveBtnClicked: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent,
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (isEnd) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = recipe.imgUrl,
                            contentDescription = stringResource(id = R.string.description_recipe_step_image),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(
                                id = when (recipeStep.type) {
                                    0 -> R.drawable.recipe_step_1
                                    1 -> R.drawable.recipe_step_2
                                    else -> R.drawable.recipe_step_3
                                }
                            ),
                            contentDescription = stringResource(id = R.string.description_recipe_step_image),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (isEnd) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 10.dp, end = 10.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable { onRecipeSaveBtnClicked() }
                                .border(2.dp, colorScheme.primary, CircleShape)
                                .background(Color.White)
                                .padding(7.dp),
                            painter = if (recipe.saveYn) painterResource(id = R.drawable.bookmark_fill) else painterResource(
                                id = R.drawable.bookmark
                            ),
                            contentDescription = stringResource(id = R.string.description_btn_bookmark),
                            colorFilter = ColorFilter.tint(colorScheme.primary)
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 20.dp),
                            text = recipe.name,
                            style = Typography.bodyLarge,
                            color = Color.White,
                        )
                    }
                }

                if (!isEnd) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = widthDp.toDpSize(7))
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(
                            space = ScreenSizeUtil.heightDp.toDpSize(7),
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${page + 1}. ${recipeStep.description}",
                            style = Typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = recipeStep.tip,
                            style = Typography.bodyMedium,
                            color = colorScheme.onSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = widthDp.toDpSize(10)),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RecipeDetailInfoRow(
                            time = recipe.cookTime,
                            calorie = recipe.calorie
                        )

                        RecipeStepCompleteBtn(
                            navigateToRecipeList = navigateToRecipeList,
                            navigateToRecipeComplete = navigateToRecipeComplete
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextToSpeechBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .size(60.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = colorScheme.primary,
            contentColor = Color.White
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(15.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Image(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(id = R.drawable.text_to_speech),
            contentDescription = stringResource(id = R.string.description_btn_tts),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun RecipeStepCompleteBtn(
    modifier: Modifier = Modifier,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = widthDp.toDpSize(10)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(colorScheme.primary)
                .clickable { navigateToRecipeComplete() }
                .padding(vertical = 10.dp),
            text = stringResource(id = R.string.text_record_recipe),
            style = Typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .border(1.dp, colorScheme.primary, RoundedCornerShape(50.dp))
                .clickable { navigateToRecipeList() }
                .background(Color.White)
                .padding(vertical = 10.dp),
            text = stringResource(id = R.string.text_go_to_recipe_list_2),
            style = Typography.bodyMedium,
            color = colorScheme.primary,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}