package com.s005.fif.user.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun UserOnboardingScreen(
    modifier: Modifier = Modifier,
    navigateToUserSelect: () -> Unit,
    navigateToMain: () -> Unit
) {
    UserOnboardingBody(
        modifier = modifier
            .fillMaxSize(),
        navigateToUserSelect = navigateToUserSelect,
        navigateToMain = navigateToMain
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserOnboardingBody(
    modifier: Modifier = Modifier,
    navigateToUserSelect: () -> Unit,
    navigateToMain: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        UserOnboardingProgressBar(
            modifier = Modifier,
            progress = pagerState.currentPage
        )

        UserOnboardingPager(
            pagerState = pagerState,
            goPrevPage = {
                if (pagerState.currentPage == 0) {
                    navigateToUserSelect()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            goNextPage = {
                if (pagerState.currentPage == 2) {
                    navigateToMain()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )
    }
}

@Composable
fun UserOnboardingProgressBar(
    modifier: Modifier = Modifier,
    progress: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .background(colorScheme.primary)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .background(if (progress == 0) colorScheme.onSecondary else colorScheme.primary)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(5.dp)
                .background(if (progress == 2) colorScheme.primary else colorScheme.onSecondary)
        )
    }
}

@Composable
fun UserOnboardingControlBar(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(25.dp)
                .clickable { goPrevPage() },
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back)
        )

        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorScheme.onSecondary,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(
                    vertical = 5.dp,
                    horizontal = 10.dp
                )
                .clickable { goNextPage() },
            text = stringResource(id = R.string.text_skip_onboarding),
            style = Typography.bodySmall,
            color = colorScheme.onSecondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserOnboardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        when (page) {
            0 -> {
                LikeFoodPage(
                    goPrevPage = goPrevPage,
                    goNextPage = goNextPage
                )
            }

            1 -> {
                DislikeIngredientPage(
                    goPrevPage = goPrevPage,
                    goNextPage = goNextPage
                )
            }

            2 -> {
                IllnessPage(
                    goPrevPage = goPrevPage,
                    goNextPage = goNextPage
                )
            }
        }
    }
}

@Composable
fun UserOnboardingPageTitle(
    modifier: Modifier = Modifier,
    titleText: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = titleText,
        style = Typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun UserOnboardingBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium
        )
    }
}
