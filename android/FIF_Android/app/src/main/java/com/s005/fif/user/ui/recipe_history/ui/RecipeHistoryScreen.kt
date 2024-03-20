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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.profile.UserProfileTopBar
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType.SavedHistory
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType.CompletedFood
import com.s005.fif.utils.ScreenSizeUtil
import kotlinx.coroutines.launch


enum class RecipeHistoryType {
    SavedHistory, CompletedFood
}

data class RecipeHistoryData(val time: Int, val name: String, val imgUrl: String)

@Composable
fun RecipeHistoryScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp
        )

        SavedRecipeBody()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedRecipeBody(
    modifier: Modifier = Modifier,
) {
    var selected by remember { mutableStateOf(SavedHistory) }
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    val list1 = listOf(
        RecipeHistoryData(30, "자장면", "https://flexible.img.hani.co.kr/flexible/normal/640/427/imgdb/original/2023/0306/20230306502777.jpg"),
        RecipeHistoryData(35, "짬뽕", "https://i.namu.wiki/i/upNZ7cYsFsAfU0KcguO6OHMK68xC-Bj8EXxdCti61Jhjx10UCBgdK5bZCEx41-aAWcjWZ5JMKFUSaUGLC1tqWg.webp"),
        RecipeHistoryData(40, "탕수육", "https://i.namu.wiki/i/NSZu9w4DRwEPOCgPSzvs4sAZlxfMBoxZLCZQgM_O4wRH8jN0guRfBiLURu-Tno5p-Q2aw5e5gy9gLJsnYKlq8Q.webp"),
        RecipeHistoryData(25, "볶음밥", "https://i.namu.wiki/i/LSHO99AHJpGzryDcM1npuUFNwzSUFYxUmXmqnmVZHOuc5iqCkNYRjRli9aX50BZ3cHz4gtPTqxldJee82Zj0Mg.webp"),
        RecipeHistoryData(50, "라면", "https://img1.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202310/20/dailylife/20231020130002135gmiu.jpg"),
        RecipeHistoryData(50, "족발", "https://i.namu.wiki/i/I63sEiy-8vUXVhV-I0IZiS9ntT0INuKXgBYAE3QqUvOlToSoEqSgpvEbUmxsFTXtoBRN4WJolyAFEAlDdeZFhQ.webp"),
        RecipeHistoryData(50, "마라탕", "https://i.namu.wiki/i/qFWfOHBd0mx7NmNquwtaSbUjnPumXpk5oi1jxNKpWUsv_eGJe44xm9AePkbhQ6hIxTjMtroFaOFPbhBy0MSbNQ.webp"),
        RecipeHistoryData(50, "순대볶음", "https://cdn.mkhealth.co.kr/news/photo/202008/img_MKH200814003_0.jpg"),
        RecipeHistoryData(50, "떡볶이", "https://i.namu.wiki/i/A5AIHovo1xwuEjs7V8-aKpZCSWY2gN3mZEPR9fymaez_J7ufmI9B7YyDBu6kZy9TC9VWJatXVJZbDjcYLO2S8Q.webp"),
        RecipeHistoryData(50, "해물찜", "https://recipe1.ezmember.co.kr/cache/recipe/2016/12/16/99d6cb0cb6c434b562217f407623c8491.jpg"),
    )
    val list2 = listOf(
        RecipeHistoryData(30, "자장면", "https://flexible.img.hani.co.kr/flexible/normal/640/427/imgdb/original/2023/0306/20230306502777.jpg"),
        RecipeHistoryData(35, "짬뽕", "https://i.namu.wiki/i/upNZ7cYsFsAfU0KcguO6OHMK68xC-Bj8EXxdCti61Jhjx10UCBgdK5bZCEx41-aAWcjWZ5JMKFUSaUGLC1tqWg.webp"),
        RecipeHistoryData(40, "탕수육", "https://i.namu.wiki/i/NSZu9w4DRwEPOCgPSzvs4sAZlxfMBoxZLCZQgM_O4wRH8jN0guRfBiLURu-Tno5p-Q2aw5e5gy9gLJsnYKlq8Q.webp"),
        RecipeHistoryData(25, "볶음밥", "https://i.namu.wiki/i/LSHO99AHJpGzryDcM1npuUFNwzSUFYxUmXmqnmVZHOuc5iqCkNYRjRli9aX50BZ3cHz4gtPTqxldJee82Zj0Mg.webp"),
        RecipeHistoryData(50, "라면", "https://img1.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202310/20/dailylife/20231020130002135gmiu.jpg"),
        RecipeHistoryData(50, "족발", "https://i.namu.wiki/i/I63sEiy-8vUXVhV-I0IZiS9ntT0INuKXgBYAE3QqUvOlToSoEqSgpvEbUmxsFTXtoBRN4WJolyAFEAlDdeZFhQ.webp"),
        RecipeHistoryData(50, "마라탕", "https://i.namu.wiki/i/qFWfOHBd0mx7NmNquwtaSbUjnPumXpk5oi1jxNKpWUsv_eGJe44xm9AePkbhQ6hIxTjMtroFaOFPbhBy0MSbNQ.webp"),
        RecipeHistoryData(50, "순대볶음", "https://cdn.mkhealth.co.kr/news/photo/202008/img_MKH200814003_0.jpg"),
        RecipeHistoryData(50, "떡볶이", "https://i.namu.wiki/i/A5AIHovo1xwuEjs7V8-aKpZCSWY2gN3mZEPR9fymaez_J7ufmI9B7YyDBu6kZy9TC9VWJatXVJZbDjcYLO2S8Q.webp"),
        RecipeHistoryData(50, "해물찜", "https://recipe1.ezmember.co.kr/cache/recipe/2016/12/16/99d6cb0cb6c434b562217f407623c8491.jpg"),
    ).reversed()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selected = if (page == 0) SavedHistory else CompletedFood
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp),

    ) {
        SavedRecipeTitleRow(
            selected = selected,
            onClick = {
                if (selected == SavedHistory) {
                    selected = CompletedFood

                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                } else {
                    selected = SavedHistory

                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            }
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
                        list = list1
                    )
                }
                1 -> {
                    // TODO. 실제 리스트로 변경 필요
                    RecipeHistoryPagerItem(
                        list = list2
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
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
    ) {
        SavedRecipeTitleItem(
            modifier = Modifier
                .weight(1f),
            icon = painterResource(id = R.drawable.bookmark_fill),
            text = stringResource(id = R.string.text_saved_recipe) + " (10)",
            iconColor = if (selected == SavedHistory) Color.White else colorScheme.primary,
            textColor = if (selected == SavedHistory) Color.White else Color.Black,
            backgroundColor = if (selected == SavedHistory) colorScheme.primary else Color.White,
            onClick = onClick
        )

        SavedRecipeTitleItem(
            modifier = Modifier
                .weight(1f),
            icon = painterResource(id = R.drawable.complete),
            text = stringResource(id = R.string.text_completed_food)+ " (10)",
            iconColor = if (selected == SavedHistory) colorScheme.primary else Color.White,
            textColor = if (selected == SavedHistory) Color.Black else Color.White,
            backgroundColor = if (selected == SavedHistory) Color.White else colorScheme.primary,
            onClick = onClick
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
    list: List<RecipeHistoryData> = listOf(
        RecipeHistoryData(30, "자장면", "https://flexible.img.hani.co.kr/flexible/normal/640/427/imgdb/original/2023/0306/20230306502777.jpg"),
        RecipeHistoryData(35, "짬뽕", "https://i.namu.wiki/i/upNZ7cYsFsAfU0KcguO6OHMK68xC-Bj8EXxdCti61Jhjx10UCBgdK5bZCEx41-aAWcjWZ5JMKFUSaUGLC1tqWg.webp"),
        RecipeHistoryData(40, "탕수육", "https://i.namu.wiki/i/NSZu9w4DRwEPOCgPSzvs4sAZlxfMBoxZLCZQgM_O4wRH8jN0guRfBiLURu-Tno5p-Q2aw5e5gy9gLJsnYKlq8Q.webp"),
        RecipeHistoryData(25, "볶음밥", "https://i.namu.wiki/i/LSHO99AHJpGzryDcM1npuUFNwzSUFYxUmXmqnmVZHOuc5iqCkNYRjRli9aX50BZ3cHz4gtPTqxldJee82Zj0Mg.webp"),
        RecipeHistoryData(50, "라면", "https://img1.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202310/20/dailylife/20231020130002135gmiu.jpg"),
        RecipeHistoryData(50, "족발", "https://i.namu.wiki/i/I63sEiy-8vUXVhV-I0IZiS9ntT0INuKXgBYAE3QqUvOlToSoEqSgpvEbUmxsFTXtoBRN4WJolyAFEAlDdeZFhQ.webp"),
        RecipeHistoryData(50, "마라탕", "https://i.namu.wiki/i/qFWfOHBd0mx7NmNquwtaSbUjnPumXpk5oi1jxNKpWUsv_eGJe44xm9AePkbhQ6hIxTjMtroFaOFPbhBy0MSbNQ.webp"),
        RecipeHistoryData(50, "순대볶음", "https://cdn.mkhealth.co.kr/news/photo/202008/img_MKH200814003_0.jpg"),
        RecipeHistoryData(50, "떡볶이", "https://i.namu.wiki/i/A5AIHovo1xwuEjs7V8-aKpZCSWY2gN3mZEPR9fymaez_J7ufmI9B7YyDBu6kZy9TC9VWJatXVJZbDjcYLO2S8Q.webp"),
        RecipeHistoryData(50, "해물찜", "https://recipe1.ezmember.co.kr/cache/recipe/2016/12/16/99d6cb0cb6c434b562217f407623c8491.jpg"),
    )
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
                onClick = {  }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeHistoryLazyVerticalGridItem(
    modifier: Modifier = Modifier,
    item: RecipeHistoryData,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((ScreenSizeUtil.screenHeightDp / 5).dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = item.imgUrl,
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
                    text = "${item.time} min",
                    style = Typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 2.dp),
                text = item.name,
                style = Typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}