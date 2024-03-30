package com.s005.fif.chat.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.s005.fif.chat.data.ChatResultObject
import com.s005.fif.chat.dto.ChatResultRecipeListItem
import com.s005.fif.chat.dto.ChatResultRecipeStep
import com.s005.fif.recipe.ui.step.RecipeStepProgressBar
import com.s005.fif.recipe.ui.step.TextToSpeechBtn
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.TTSUtil
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRecipeStepScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel,
    recipeIdx: Int,
    navigateUp: () -> Unit,
    popBackStackToChat: () -> Unit,
) {
    val recipe = chatViewModel.recipeList[recipeIdx]
    val recipeStepList = chatViewModel.recipeList[recipeIdx].recipeSteps
    val pagerState = rememberPagerState(pageCount = { recipeStepList.size + 1 })

    BackHandler {
        TTSUtil.stop()
        navigateUp()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 30.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        ChatRecipeStepTopBar(
            navigateUp = navigateUp,
            title = recipe.name,
            isEnd = pagerState.currentPage == pagerState.pageCount - 1
        )

        ChatRecipeStepBody(
            modifier = Modifier,
            recipe = recipe,
            recipeStepList = recipeStepList,
            pagerState = pagerState,
            popBackStackToChat = popBackStackToChat,
        )
    }
}

@Composable
fun ChatRecipeStepTopBar(
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
fun ChatRecipeStepBody(
    modifier: Modifier = Modifier,
    recipe: ChatResultRecipeListItem,
    recipeStepList: List<ChatResultRecipeStep>,
    pagerState: PagerState,
    popBackStackToChat: () -> Unit,
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
            pageSize = PageSize.Fixed(ScreenSizeUtil.widthDp.toDpSize(80)),
            pageSpacing = 10.dp,
            contentPadding = PaddingValues(horizontal = ScreenSizeUtil.widthDp.toDpSize(10))
        ) { page ->
            ChatRecipeStepPage(
                modifier = pageModifier(page),
                page = page,
                isEnd = isEnd,
                recipe = recipe,
                recipeStep = recipeStepList[minOf(page, pagerState.pageCount - 2)],
                popBackStackToChat = popBackStackToChat
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
fun ChatRecipeStepProgressBar(
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
            color = MaterialTheme.colorScheme.primary
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
fun ChatRecipeStepPage(
    modifier: Modifier = Modifier,
    page: Int,
    isEnd: Boolean,
    recipe: ChatResultRecipeListItem,
    recipeStep: ChatResultRecipeStep,
    popBackStackToChat: () -> Unit,
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
                            model = ChatResultObject.defaultRecipeImg,
                            contentDescription = stringResource(id = R.string.description_recipe_step_image),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(
                                id = when (recipeStep.type.replace(" ", "")) {
                                    "재료손질" -> R.drawable.recipe_step_1
                                    "조리" -> R.drawable.recipe_step_2
                                    else -> R.drawable.recipe_step_3
                                }
                            ),
                            contentDescription = stringResource(id = R.string.description_recipe_step_image),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (isEnd) {
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
                            .padding(horizontal = ScreenSizeUtil.widthDp.toDpSize(7))
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
                            color = MaterialTheme.colorScheme.onSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = ScreenSizeUtil.widthDp.toDpSize(10)),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ChatRecipeDetailInfoRow(
                            time = if (!recipe.cookTime.contains("분") && !recipe.cookTime.contains("m") && !recipe.cookTime.contains("시간")
                            ) {
                                "$recipe.cookTime 분"
                            } else {
                                recipe.cookTime
                            },
                            calorie = if ((recipe.calorie ?: recipe.carlorie) != null) {
                                val calorieText = (recipe.calorie ?: recipe.carlorie)!!

                                if (!calorieText.contains("cal")) {
                                    "$calorieText kcal"
                                } else {
                                    calorieText
                                }
                            } else {
                                stringResource(id = R.string.text_no_calorie_data)
                            }
                        )

                        ChatRecipeStepCompleteBtn(
                            popBackStackToChat = popBackStackToChat,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatRecipeStepCompleteBtn(
    modifier: Modifier = Modifier,
    popBackStackToChat: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenSizeUtil.widthDp.toDpSize(10)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { popBackStackToChat() }
                .padding(vertical = 10.dp),
            text = stringResource(id = R.string.text_recipe_end),
            style = Typography.bodyMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}