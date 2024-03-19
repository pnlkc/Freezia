package com.s005.fif.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.profile.HealthCard
import com.s005.fif.user.ui.profile.UserProfileBody
import com.s005.fif.user.ui.profile.UserProfileColumnText
import com.s005.fif.user.ui.profile.UserProfileTopBar

data class MainData(val imgUrl: String, val desc: String)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeChat: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp)
            .background(colorScheme.background)
    ) {
        MainTopBar()

        MainBody(
            navigateToUserProfile = navigateToUserProfile,
            navigateToRecipeList = navigateToRecipeList,
            navigateToRecipeChat = navigateToRecipeChat
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement .SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 5.dp),
            text = "Fridge is Free",
            style = Typography.bodyLarge,
            color = colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = stringResource(id = R.string.description_btn_bookmark)
            )

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.notification),
                contentDescription = stringResource(id = R.string.description_btn_notification)
            )

            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { },
                model = "https://img.danawa.com/prod_img/500000/207/533/img/18533207_1.jpg?_v=20221226163359",
                contentDescription = stringResource(id = R.string.description_img_profile),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeChat: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        MainHealthColumn(
            navigateToUserProfile = navigateToUserProfile
        )

        MainRecipeRecommendColumn()

        MainRecipeBtnRow(
            navigateToRecipeList = navigateToRecipeList,
            navigateToRecipeChat = navigateToRecipeChat
        )
    }
}

@Composable
fun MainHealthColumn(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfileColumnText(
                text = stringResource(id = R.string.text_health_title, "김싸피")
            )

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { navigateToUserProfile() },
                painter = painterResource(id = R.drawable.setting),
                contentDescription = stringResource(id = R.string.description_btn_recipe_preference_setting)
            )
        }

        HealthCard()
    }
}

@Composable
fun MainRecipeRecommendColumn(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = stringResource(id = R.string.text_recipe_recommend_title)
        )

        MainRecipeRecommendPager()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainRecipeRecommendPager(
    modifier: Modifier = Modifier,
) {
    // TODO. 실제 리스트로 변경 필요
    val list = listOf(
        MainData("https://flexible.img.hani.co.kr/flexible/normal/640/427/imgdb/original/2023/0306/20230306502777.jpg", "스트레스가 높으신 날\n자장면 어떠세요?"),
        MainData("https://i.namu.wiki/i/upNZ7cYsFsAfU0KcguO6OHMK68xC-Bj8EXxdCti61Jhjx10UCBgdK5bZCEx41-aAWcjWZ5JMKFUSaUGLC1tqWg.webp", "스트레스가 높으신 날\n" +
                "짬뽕 어떠세요?"),
        MainData("https://i.namu.wiki/i/NSZu9w4DRwEPOCgPSzvs4sAZlxfMBoxZLCZQgM_O4wRH8jN0guRfBiLURu-Tno5p-Q2aw5e5gy9gLJsnYKlq8Q.webp", "스트레스가 높으신 날\n" +
                "탕수육 어떠세요?"),
    )

    val pagerState = rememberPagerState(pageCount = { list.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 20.dp
        ) { page ->
            MainRecipeRecommendCard(
                item = list[page]
            )
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) colorScheme.primary else Color.White
                val borderColor = if (pagerState.currentPage == iteration) Color.Transparent else colorScheme.primary

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                        .border(1.dp, borderColor, CircleShape)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainRecipeRecommendCard(
    modifier: Modifier = Modifier,
    item: MainData
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(10.dp)),
                model = item.imgUrl,
                contentDescription = stringResource(id = R.string.description_recipe_img),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.2f), BlendMode.Overlay)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.desc,
                    style = Typography.bodyMedium
                )

                Text(
                    modifier = modifier
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { }
                        .background(colorScheme.primary)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    text = stringResource(id = R.string.text_go_to_recipe_list),
                    style = Typography.bodyMedium,
                    color = colorScheme.onPrimary,
                )
            }
        }
    }
}

@Composable
fun MainRecipeBtnRow(
    modifier: Modifier = Modifier,
    navigateToRecipeList: () -> Unit,
    navigateToRecipeChat: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MainRecipeBtnCard(
            modifier = Modifier
                .weight(1f),
            imgUrl = "https://static.news.zumst.com/images/35/2019/05/31/e106d68c2660479db41ba09739c49539.JPG",
            text = stringResource(id = R.string.text_recipe_list),
            contentDescription = stringResource(id = R.string.description_recipe_list_icon),
            icon = painterResource(id = R.drawable.playlist_add_check),
            onClick = { navigateToRecipeList() }
        )

        MainRecipeBtnCard(
            modifier = Modifier
                .weight(1f),
            imgUrl = "https://statics.vinpearl.com/%EB%B2%A0%ED%8A%B8%EB%82%A8%EC%9A%94%EB%A6%AC-1_1666454074.jpg",
            text = stringResource(id = R.string.text_recipe_gpt),
            contentDescription = stringResource(id = R.string.description_recipe_gpt_icon),
            icon = painterResource(id = R.drawable.neurology),
            onClick = { navigateToRecipeChat() }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainRecipeBtnCard(
    modifier: Modifier = Modifier,
    imgUrl: String,
    text: String,
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = imgUrl,
                contentDescription = stringResource(id = R.string.description_recipe_img),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.3f), BlendMode.ColorBurn)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(20.dp)
                        .background(colorScheme.primary)
                        .padding(2.dp),
                    painter = icon,
                    contentDescription = contentDescription,
                    tint = Color.White
                )

                Text(
                    text = text,
                    style = Typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}