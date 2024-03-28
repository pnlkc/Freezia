package com.s005.fif.recipe.ui.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.step.RecipeStepTopBar
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn
import com.s005.fif.user.ui.onboarding.UserOnboardingPageTitle

@Composable
fun IngredientRemoveScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeId: Int,
    navigateUp: () -> Unit,
) {
    val recipe = recipeViewModel.getRecipe(recipeId)!!

    LaunchedEffect(key1 = recipeId) {
        recipeViewModel.initRemoveIngredientList(recipeId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        RecipeStepTopBar(
            navigateUp = navigateUp,
            title = "",
            isEnd = true
        )

        IngredientRemoveScreen(
            modifier = modifier
                .fillMaxSize(),
            checkItem = { id, isChecked ->
                recipeViewModel.checkRemoveIngredient(id, isChecked)
            },
            list = recipeViewModel.removeIngredientList,
            completeBtnClicked = navigateUp
        )
    }


}

@Composable
fun IngredientRemoveScreen(
    modifier: Modifier = Modifier,
    checkItem: (Int, Boolean) -> Unit,
    list: List<IngredientItem>,
    completeBtnClicked: () -> Unit,
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
                    titleText = stringResource(id = R.string.text_remove_ingredient_title)
                )
            }

            item {
                Box(
                    modifier = Modifier.size(30.dp)
                )
            }

            itemsIndexed(
                items = list,
                key = { _, item ->
                    item.ingredientId
                }
            ) { _, item ->
                IngredientRemoveItem(
                    item = item,
                    isChecked = item.isChecked,
                    onCheckBtnClicked = checkItem
                )
            }
        }

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = completeBtnClicked,
            text = stringResource(id = R.string.text_btn_complete)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientRemoveItem(
    modifier: Modifier = Modifier,
    item: IngredientItem,
    isChecked: Boolean,
    onCheckBtnClicked: (Int, Boolean) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .toggleable(
                value = isChecked,
                onValueChange = { onCheckBtnClicked(item.ingredientId, !isChecked) },
                role = Role.Checkbox
            ),
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
                model = item.image,
                contentDescription = stringResource(id = R.string.description_ingredient_img),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.basic_ingredient),
                failure = placeholder(R.drawable.basic_ingredient)
            )

            Text(
                text = item.name,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }

        Checkbox(
            checked = isChecked,
            onCheckedChange = null, // null recommended for accessibility with screenreaders
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}