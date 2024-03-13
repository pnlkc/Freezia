package com.s005.fif.recipe.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.Text
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.components.BackgroundImage
import com.s005.fif.utils.DummyImageUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import com.s005.fif.utils.TTSUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
) {
    val maxPages = 2
    var selectedPage by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = selectedPage,
        pageCount = { maxPages + 1 }
    )
    var finalValue by remember { mutableIntStateOf(0) }

    val animatedSelectedPage by animateFloatAsState(
        targetValue = selectedPage.toFloat(),
        label = "",
    ) {
        finalValue = it.toInt()
    }

    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = animatedSelectedPage - finalValue
            override val selectedPage: Int
                get() = finalValue
            override val pageCount: Int
                get() = maxPages
        }
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedPage = page
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        BackgroundImage(
            imgUrl = DummyImageUtil.list.random()
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(ScreenSize.screenHeightDp.toDpSize(5)),
            state = pagerState
        ) { page ->
            if (page != maxPages) {
                RecipeDetailBody(
                    page = page,
                    maxPage = maxPages,
                    goStepBack = {
                        if (selectedPage > 0) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(--selectedPage)
                            }
                        }
                    },
                    goStepForward = {
                        if (selectedPage < maxPages) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(++selectedPage)
                            }
                        }
                    }
                )
            } else {
                RecipeDoneBody(
                    navigateToMain = navigateToMain
                )
            }
        }

        if (pagerState.currentPage < maxPages) {
            HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeDetailBody(
    modifier: Modifier = Modifier,
    page: Int,
    maxPage: Int,
    goStepBack: () -> Unit,
    goStepForward: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = ScreenSize.screenHeightDp.toDpSize(7)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "${page + 1} / ${maxPage}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(7),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "${page + 1}. 재료를 손질합니다",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(9),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = ScreenSize.screenHeightDp.toDpSize(5),
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Chip(
                modifier = Modifier
                    .height(ScreenSize.screenHeightDp.toDpSize(18)),
                label = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.use_timer),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = ScreenSize.screenHeightDp.toSpSize(8),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = ChipDefaults.primaryChipColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White
                ),
                onClick = { },
                shape = MaterialTheme.shapes.large
            )

            ControlBtnRow(
                goStepBack = goStepBack,
                goStepForward = goStepForward
            )
        }
    }
}

@Composable
fun ControlBtnRow(
    modifier: Modifier = Modifier,
    goStepBack: () -> Unit,
    goStepForward: () -> Unit,
) {
    val btnSize = ScreenSize.screenHeightDp.toDpSize(15)
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = ScreenSize.screenHeightDp.toDpSize(2),
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Icon(
            modifier = Modifier
                .size(btnSize)
                .clip(CircleShape)
                .clickable { goStepBack() }
                .padding(ScreenSize.screenHeightDp.toDpSize(2)),
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = stringResource(id = R.string.btn_recipe_step_back),
            tint = Color.White
        )

        Icon(
            modifier = Modifier
                .size(btnSize)
                .clip(CircleShape)
                .clickable {
                    val tts = TTSUtil()
                    tts.speak(context = context, text = "TTS 테스트 입니다")
                }
                .padding(ScreenSize.screenHeightDp.toDpSize(2)),
            painter = painterResource(id = R.drawable.speaker),
            contentDescription = stringResource(id = R.string.btn_recipe_step_tts),
            tint = Color.White
        )

        Icon(
            modifier = Modifier
                .size(btnSize)
                .clip(CircleShape)
                .clickable { goStepForward() }
                .padding(ScreenSize.screenHeightDp.toDpSize(2)),
            painter = painterResource(id = R.drawable.arrow_forward),
            contentDescription = stringResource(id = R.string.btn_recipe_step_forward),
            tint = Color.White
        )
    }
}

@Composable
fun RecipeDoneBody(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = ScreenSize.screenHeightDp.toDpSize(20),
                bottom = ScreenSize.screenHeightDp.toDpSize(7)
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.recipe_done_message),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(9),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Chip(
            modifier = Modifier
                .height(ScreenSize.screenHeightDp.toDpSize(18)),
            label = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.recipe_done),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = ScreenSize.screenHeightDp.toSpSize(6),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = ChipDefaults.primaryChipColors(
                backgroundColor = MaterialTheme.colors.onSecondary,
                contentColor = Color.White
            ),
            onClick = { navigateToMain() },
            shape = MaterialTheme.shapes.large
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigateToMain() },
            text = stringResource(id = R.string.record_panel),
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(6),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}