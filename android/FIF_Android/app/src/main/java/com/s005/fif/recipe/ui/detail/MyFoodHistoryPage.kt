package com.s005.fif.recipe.ui.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.common.BasicAlertDialog
import com.s005.fif.recipe.dto.CompleteRecipeRecordIngredient
import com.s005.fif.recipe.dto.CompleteRecipeRecordItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun MyFoodHistoryPage(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeId: Int
) {
    val coroutineScope = rememberCoroutineScope()

    if (recipeViewModel.completeRecipeRecordList.isEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = ScreenSizeUtil.widthDp.toDpSize(30)),
            text = stringResource(id = R.string.text_no_recipe_history),
            textAlign = TextAlign.Center,
            style = Typography.bodyLarge,
            color = Color.Black
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(3.dp)
        ) {
            itemsIndexed(
                items = recipeViewModel.completeRecipeRecordList,
                key = { _, item ->
                    item.completeCookId
                }
            ) { _, item ->
                MyFoodHistoryItem(
                    modifier = Modifier,
                    item = item,
                    onItemLongClicked = {
                        coroutineScope.launch {
                            recipeViewModel.deleteRecipeHistory(item.completeCookId, recipeId)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyFoodHistoryItem(
    modifier: Modifier = Modifier,
    item: CompleteRecipeRecordItem,
    onItemLongClicked: () -> Unit,
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = { },
                    onLongClick = {
                        openAlertDialog = true
                    }
                )
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = item.completeDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")),
                style = Typography.titleSmall
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MyFoodHistoryIngredientList(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.text_add_ingredient),
                    img = painterResource(id = R.drawable.add_circle),
                    desc = stringResource(id = R.string.description_add_ingredient),
                    list = item.addIngredients
                )

                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight(),
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(0.2f)
                )

                MyFoodHistoryIngredientList(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.text_remove_ingredient),
                    img = painterResource(id = R.drawable.remove_circle),
                    desc = stringResource(id = R.string.description_remove_ingredient),
                    list = item.removeIngredients
                )
            }
        }
    }

    if (openAlertDialog) {
        BasicAlertDialog(
            onDismissRequest = { openAlertDialog = false },
            onConfirmation = {
                onItemLongClicked()
                openAlertDialog = false
            },
            dialogTitle = "요리 기록을 삭제하시겠습까?",
            dialogText = "${item.completeDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))} 요리 기록이 삭제됩니다\n삭제된 요리 기록은 복구할 수 없습니다",
            icon = null,
        )
    }
}

@Composable
fun MyFoodHistoryIngredientList(
    modifier: Modifier = Modifier,
    text: String,
    img: Painter,
    desc: String,
    list: List<CompleteRecipeRecordIngredient>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),
                painter = img,
                contentDescription = desc,
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )

            Text(
                text = text,
                style = Typography.bodyMedium
            )
        }

        list.forEach { item ->
            MyFoodHistoryIngredientItem(
                item = item
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyFoodHistoryIngredientItem(
    modifier: Modifier = Modifier,
    item: CompleteRecipeRecordIngredient,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp),
            model = item.image,
            contentDescription = stringResource(id = R.string.description_ingredient_img),
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.basic_ingredient),
            failure = placeholder(R.drawable.basic_ingredient)
        )

        Text(
            text = item.name,
            style = Typography.bodyMedium,
            maxLines = 1
        )
    }
}