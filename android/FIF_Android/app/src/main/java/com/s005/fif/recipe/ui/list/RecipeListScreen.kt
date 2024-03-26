package com.s005.fif.recipe.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.RecipeItem
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.profile.UserProfileTopBar
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryLazyVerticalGridItem
import com.s005.fif.utils.ScreenSizeUtil
import kotlinx.coroutines.delay

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToRecipeChat: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = { navigateUp() },
            memberInfo = userViewModel.memberInfo
        )

        RecipeListBody(
            navigateToRecipeChat = navigateToRecipeChat,
            navigateToRecipeDetail = navigateToRecipeDetail
        )
    }
}

@Composable
fun RecipeListBody(
    modifier: Modifier = Modifier,
    navigateToRecipeChat: () -> Unit,
    navigateToRecipeDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecipeListTagList()

        RecipeListLazyGrid(
            navigateToRecipeChat = navigateToRecipeChat,
            navigateToRecipeDetail = navigateToRecipeDetail,
            list = listOf()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeListTagList(
    modifier: Modifier = Modifier,
) {
    val list = listOf(
        "한식",
        "중식",
        "양식",
        "일식",
        "밑반찬",
        "면 요리",
        "국물 요리",
        "볶음 요리",
        "찜 요리",
        "유통기한 임박"
    )

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        list.forEach { item ->
            RecipeListTagItem(
                item = item
            )
        }
    }
}

@Composable
fun RecipeListTagItem(
    modifier: Modifier = Modifier,
    item: String,
) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .clickable { isSelected = !isSelected }
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
            .padding(vertical = 5.dp, horizontal = 10.dp),
        text = item,
        style = Typography.bodyMedium,
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Black,
        maxLines = 1
    )
}

@Composable
fun RecipeListLazyGrid(
    modifier: Modifier = Modifier,
    list: List<RecipeItem>,
    navigateToRecipeChat: () -> Unit,
    navigateToRecipeDetail: () -> Unit
) {
    var isShow by remember {
        mutableStateOf(false)
    }

    // 10초 뒤에 "원하는 레시피가 없나요?" 문구 노출
    LaunchedEffect(key1 = isShow) {
        delay(10000L)
        isShow = true
    }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            RecipeListRecipeGPTBtn(
                isShow = isShow,
                navigateToRecipeChat = navigateToRecipeChat
            )
        }

        itemsIndexed(
            items = list,
            key = { _, item ->
                item.imgUrl
            }
        ) { _, item ->
            RecipeHistoryLazyVerticalGridItem(
                item = item,
                onClick = { navigateToRecipeDetail() }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeListRecipeGPTBtn(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    navigateToRecipeChat: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height((ScreenSizeUtil.heightDp / 5).dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { navigateToRecipeChat() },
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = "https://statics.vinpearl.com/%EB%B2%A0%ED%8A%B8%EB%82%A8%EC%9A%94%EB%A6%AC-1_1666454074.jpg",
            contentDescription = stringResource(id = R.string.description_recipe_img),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.4f), BlendMode.ColorBurn)
        )

        AnimatedVisibility(
            modifier = modifier
                .align(Alignment.TopCenter),
            visible = isShow,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                modifier = modifier
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = stringResource(id = R.string.text_no_exist_want_recipe),
                style = Typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

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
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(2.dp),
                painter = painterResource(id = R.drawable.neurology),
                contentDescription = stringResource(id = R.string.description_recipe_gpt_icon),
                tint = Color.White
            )

            Text(
                text = stringResource(id = R.string.text_recipe_gpt),
                style = Typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
