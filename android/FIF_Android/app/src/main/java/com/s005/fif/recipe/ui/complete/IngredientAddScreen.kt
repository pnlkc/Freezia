package com.s005.fif.recipe.ui.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.step.RecipeStepTopBar
import com.s005.fif.user.ui.onboarding.DislikeIngredientSearchResultItem
import com.s005.fif.user.ui.onboarding.DislikeIngredientSelectLazyRow
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn
import com.s005.fif.user.ui.onboarding.UserOnboardingPageTitle
import com.s005.fif.user.ui.onboarding.UserProfileTextField

@Composable
fun IngredientAddScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .background(colorScheme.background)
    ) {
        RecipeStepTopBar(
            navigateUp = navigateUp,
            title = "",
            isEnd = true
        )

        IngredientAddBody(
            modifier = modifier
                .fillMaxSize(),
            inputText = recipeViewModel.inputText,
            inputTextChange = {
                recipeViewModel.inputText = it
            },
            addItem = { item ->
                recipeViewModel.addTempAddIngredient(item)
            },
            removeItem = { item ->
                recipeViewModel.removeTempAddIngredient(item)
            },
            list = recipeViewModel.addTempIngredientList,
            completeBtnClicked = {
                recipeViewModel.addAddIngredient()
                navigateUp()
            }
        )
    }
}

@Composable
fun IngredientAddBody(
    modifier: Modifier = Modifier,
    inputText: String,
    inputTextChange: (String) -> Unit,
    addItem: (IngredientItemData) -> Unit,
    removeItem: (IngredientItemData) -> Unit,
    list: List<IngredientItemData>,
    completeBtnClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(top = 20.dp)
        ) {
            item {
                UserOnboardingPageTitle(
                    titleText = stringResource(id = R.string.text_add_ingredient_title)
                )
            }

            item {
                UserProfileTextField(
                    content = inputText,
                    setContent = inputTextChange,
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
                    item.ingredientId
                }
            ) { _, item ->
                DislikeIngredientSearchResultItem(
                    item = item,
                    onItemClicked = { _, item ->
                        addItem(item)
                    }
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
            dislikeIngredientList = list,
            onItemClicked = { _, item ->
                removeItem(item)
            }
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = { completeBtnClicked() },
            text = stringResource(id = R.string.text_btn_complete)
        )
    }
}