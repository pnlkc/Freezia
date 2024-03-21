package com.s005.fif.recipe.ui.step

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import com.s005.fif.recipe.ui.detail.RecipeDetailInfoRow
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.ScreenSizeUtil.widthDp
import com.s005.fif.utils.TTSUtil
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeStepScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 + 1 })
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
            title = "스팸 마요 김치 덮밥",
            isEnd = pagerState.currentPage == pagerState.pageCount - 1
        )

        RecipeStepBody(
            modifier = Modifier,
            pagerState = pagerState,
            navigateToRecipeList = navigateToRecipeList,
            navigateToRecipeComplete = navigateToRecipeComplete
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

        if (!isEnd) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text =  title,
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
    pagerState: PagerState,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit
) {
    val context = LocalContext.current
    val isEnd = pagerState.currentPage == pagerState.pageCount - 1
    var progress by remember {
        mutableFloatStateOf(0f)
    }

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
        if (!isEnd) {
            RecipeStepProgressBar(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                progress = progress,
                page = pagerState.currentPage + 1,
                maxPage = pagerState.pageCount - 1
            )
        } else {
            Text(
                modifier = Modifier,
                text =  stringResource(id = R.string.text_complete_recipe_title),
                style = Typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalPager(
            modifier = modifier
                .weight(1f),
            state = pagerState,
            pageSize = PageSize.Fixed(widthDp.toDpSize(80)),
            pageSpacing = 10.dp,
            contentPadding = PaddingValues(horizontal = widthDp.toDpSize(10))
        ) { page ->
            if (!isEnd) {
                RecipeStepPage(
                    modifier = pageModifier(page),
                    page = page
                )
            } else {
                RecipeCompletePage(
                    modifier = pageModifier(page),
                    navigateToRecipeList = navigateToRecipeList,
                    navigateToRecipeComplete = navigateToRecipeComplete

                )
            }
        }

        if (!isEnd) {
            TextToSpeechBtn(
                onClick = {
                    TTSUtil.speak(context, "재료를 손질합니다")
                }
            )
        } else {
            Box(
                modifier = Modifier.size(60.dp)
            )
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
            text = "${page}/${maxPage}",
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
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    model = "https://www.sommeliertimes.com/news/photo/202108/19305_43777_3937.jpg",
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(
                        space = ScreenSizeUtil.heightDp.toDpSize(10),
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${page + 1}. 재료를 손질합니다",
                        style = Typography.bodyLarge,
                    )

                    Text(
                        text = "tip: 팁입니다.팁입니다.팁입니다.팁입니다.",
                        style = Typography.bodyMedium,
                        color = colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCompletePage(
    modifier: Modifier = Modifier,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeComplete: () -> Unit
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
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = "https://static.wtable.co.kr/image/production/service/recipe/1967/bfbec835-45b4-4e15-a658-ec4f1947ba2e.jpg?size=800x800",
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    
                    Image(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp, end = 10.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { }
                            .border(2.dp, colorScheme.primary, CircleShape)
                            .background(Color.White)
                            .padding(7.dp),
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = stringResource(id = R.string.description_btn_bookmark),
                        colorFilter = ColorFilter.tint(colorScheme.primary)
                    )
                }
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = widthDp.toDpSize(10)),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecipeDetailInfoRow(
                        time = "10",
                        calorie = "400"
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


@Composable
fun TextToSpeechBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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
    navigateToRecipeComplete: () -> Unit
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