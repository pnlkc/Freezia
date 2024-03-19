package com.s005.fif.user.ui.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize

@Composable
fun UserSelectScreen(
    modifier: Modifier = Modifier,
    navigateToUserOnboarding: () -> Unit
) {
    UserSelectBody(
        modifier = modifier
            .fillMaxSize(),
        navigateToUserOnboarding = navigateToUserOnboarding
    )
}

@Composable
fun UserSelectBody(
    modifier: Modifier = Modifier,
    navigateToUserOnboarding: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            text = stringResource(id = R.string.text_select_user),
            textAlign = TextAlign.Center,
            style = Typography.titleLarge,
            color = colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            columns = GridCells.Adaptive(minSize = ScreenSizeUtil.screenWidthDp.toDpSize(30)),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            item {
                UserAddItem(
                    modifier = Modifier,
                )
            }

            itemsIndexed(
                items = listOf<String>("김싸피", "이싸피", "박싸피"),
                key = { _, item ->
                    item
                }
            ) { _, item ->
                UserSelectItem(
                    modifier = Modifier,
                    item = item,
                    navigateToUserOnboarding = navigateToUserOnboarding
                )
            }
        }
    }
}

@Composable
fun UserAddItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size((ScreenSizeUtil.screenWidthDp / 3 - 40).dp)
                .border(5.dp, colorScheme.primary, CircleShape)
        ) {
            Image(
                modifier = modifier
                    .size((ScreenSizeUtil.screenWidthDp / 3 - 60).dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.add),
                contentDescription = stringResource(id = R.string.description_btn_profile_add),
                colorFilter = ColorFilter.tint(colorScheme.primary),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = stringResource(id = R.string.text_add_user),
            style = Typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserSelectItem(
    modifier: Modifier = Modifier,
    item: String,
    navigateToUserOnboarding: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { navigateToUserOnboarding() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GlideImage(
            modifier = modifier
                .clip(CircleShape)
                .size((ScreenSizeUtil.screenWidthDp / 3 - 40).dp),
            model = "https://img.danawa.com/prod_img/500000/207/533/img/18533207_1.jpg?_v=20221226163359",
            contentDescription = stringResource(id = R.string.description_img_profile),
            contentScale = ContentScale.Crop
        )

        Text(
            text = item,
            style = Typography.bodyMedium
        )
    }
}