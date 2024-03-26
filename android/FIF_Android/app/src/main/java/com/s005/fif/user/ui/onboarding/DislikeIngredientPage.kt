package com.s005.fif.user.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel

@Composable
fun DislikeIngredientPage(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
) {
    BackHandler {
        goPrevPage()
    }

    DislikeIngredientBody(
        modifier = modifier
            .fillMaxSize(),
        goPrevPage = goPrevPage,
        goNextPage = goNextPage,
        inputText = viewModel.dislikeInputText,
        inputTextChange = {
            viewModel.dislikeInputText = it
        },
        dislikeIngredientList = viewModel.onboardingState.dislikeIngredients,
        onItemClicked = { isAdd, item ->
            viewModel.clickDislikeIngredientItem(isAdd, item)
        }
    )
}

@Composable
fun DislikeIngredientBody(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
    inputText: String,
    inputTextChange: (String) -> Unit,
    dislikeIngredientList: List<IngredientItemData>,
    onItemClicked: (Boolean, IngredientItemData) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
            .padding(bottom = 20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                UserOnboardingControlBar(
                    modifier = Modifier,
                    goPrevPage = goPrevPage,
                    goNextPage = goNextPage
                )
            }

            item {
                UserOnboardingPageTitle(
                    titleText = stringResource(id = R.string.text_dislike_ingredient_title)
                )
            }

            item {
                UserProfileTextField(
                    content = inputText,
                    setContent = {
                        inputTextChange(it)
                    },
                    hintText = stringResource(id = R.string.text_field_hint_dislike_ingredient)
                )
            }

            itemsIndexed(
                items = if (inputText.isBlank()) {
                    IngredientListData.list
                } else {
                    IngredientListData.list.filter { it.name.contains(inputText) }
                },
                key = { _, item ->
                    item.name
                }
            ) { _, item ->
                DislikeIngredientSearchResultItem(
                    item = item,
                    onItemClicked = onItemClicked
                )
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        HorizontalDivider(
            modifier = Modifier,
            thickness = 1.dp,
            color = Color.Black.copy(0.1f),
        )

        DislikeIngredientSelectLazyRow(
            dislikeIngredientList = dislikeIngredientList,
            onItemClicked = onItemClicked
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = { goNextPage() },
            text = stringResource(id = R.string.text_btn_next)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DislikeIngredientSearchResultItem(
    modifier: Modifier = Modifier,
    item: IngredientItemData,
    onItemClicked: (Boolean, IngredientItemData) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClicked(true, item) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size(30.dp),
                model = item.imgUrl,
                contentDescription = stringResource(id = R.string.description_ingredient_img),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.name,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }

        Image(
            modifier = modifier
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .size(20.dp),
            painter = painterResource(id = R.drawable.add),
            contentDescription = stringResource(id = R.string.description_icon_add),
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DislikeIngredientSelectLazyRow(
    modifier: Modifier = Modifier,
    dislikeIngredientList: List<IngredientItemData>,
    onItemClicked: (Boolean, IngredientItemData) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        reverseLayout = true
    ) {
        itemsIndexed(
            items = dislikeIngredientList,
            key = { _, item ->
                item.ingredientId
            }
        ) { _, item ->
            DislikeIngredientSelectItem(
                modifier = Modifier,
                item = item,
                onItemClicked = onItemClicked
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DislikeIngredientSelectItem(
    modifier: Modifier = Modifier,
    item: IngredientItemData,
    onItemClicked: (Boolean, IngredientItemData) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .padding(2.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.onPrimary)
    ) {
        Row(
            modifier = modifier
                .clickable { onItemClicked(false, item) }
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size(25.dp),
                model = item.imgUrl,
                contentDescription = stringResource(id = R.string.description_ingredient_img),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.name,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )

            Image(
                modifier = modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.close),
                contentDescription = stringResource(id = R.string.description_icon_remove),
                colorFilter = ColorFilter.tint(colorScheme.onSecondary),
                contentScale = ContentScale.Fit
            )
        }
    }
}