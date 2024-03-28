package com.s005.fif.recipe.ui.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.step.RecipeStepTopBar
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn
import com.s005.fif.utils.ScreenSizeUtil
import com.s005.fif.utils.ScreenSizeUtil.toDpSize
import com.s005.fif.utils.ScreenSizeUtil.widthDp
import kotlinx.coroutines.launch

@Composable
fun RecipeCompleteScreen(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipeId: Int,
    navigateUp: () -> Unit,
    navigateToRecipeHistory: () -> Unit,
    navigateToIngredientAdd: () -> Unit,
    navigateToIngredientRemove: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .background(colorScheme.background)
    ) {
        RecipeStepTopBar(
            navigateUp = navigateUp,
            title = stringResource(id = R.string.text_record_recipe),
            isEnd = false
        )

        RecipeCompleteBody(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            navigateToRecipeHistory = {
                coroutineScope.launch {
                    recipeViewModel.addRecipeHistory(recipeId)
                }

                navigateToRecipeHistory()
            },
            navigateToIngredientAdd = navigateToIngredientAdd,
            navigateToIngredientRemove = {
                navigateToIngredientRemove(recipeId)
            },
            recipe = recipeViewModel.getRecipe(recipeId)!!,
            addIngredientList = recipeViewModel.addIngredientList,
            removeIngredientList = recipeViewModel.removeIngredientList.filter { it.isChecked },
            onAddIngredientItemClicked = { id ->
                recipeViewModel.removeAddIngredient(id)
            },
            onRemoveIngredientItemClicked = { id ->
                recipeViewModel.checkRemoveIngredient(id, false)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCompleteBody(
    modifier: Modifier = Modifier,
    navigateToRecipeHistory: () -> Unit,
    navigateToIngredientAdd: () -> Unit,
    navigateToIngredientRemove: () -> Unit,
    recipe: RecipeListItem,
    addIngredientList: List<IngredientItem>,
    removeIngredientList: List<IngredientItem>,
    onAddIngredientItemClicked: (Int) -> Unit,
    onRemoveIngredientItemClicked: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = recipe.imgUrl,
                contentDescription = stringResource(id = R.string.description_recipe_img),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.2f), BlendMode.ColorBurn)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = recipe.name,
                style = Typography.bodyLarge,
                color = Color.White,
            )
        }

        RecipeCompleteIngredientList(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_add_ingredient) + " (${addIngredientList.size})",
            onAddBtnClicked = { navigateToIngredientAdd() },
            ingredientList = addIngredientList,
            onIngredientItemClicked = onAddIngredientItemClicked
        )

        RecipeCompleteIngredientList(
            modifier = Modifier
                .weight(1f),
            title = stringResource(id = R.string.text_remove_ingredient) + " (${removeIngredientList.size})",
            onAddBtnClicked = { navigateToIngredientRemove() },
            ingredientList = removeIngredientList,
            onIngredientItemClicked = onRemoveIngredientItemClicked
        )

        UserOnboardingBtn(
            modifier = Modifier,
            text = stringResource(id = R.string.text_btn_save),
            onClick = navigateToRecipeHistory
        )
    }
}

@Composable
fun RecipeCompleteIngredientList(
    modifier: Modifier = Modifier,
    title: String,
    onAddBtnClicked: () -> Unit,
    onIngredientItemClicked: (Int) -> Unit,
    ingredientList: List<IngredientItem>
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = Typography.titleSmall,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(colorScheme.primary)
                        .clickable { onAddBtnClicked() }
                        .padding(vertical = 5.dp)
                        .padding(start = 5.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(15.dp),
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = stringResource(id = R.string.description_add_ingredient),
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Text(
                        text = stringResource(id = R.string.text_add_ingredient_btn),
                        style = Typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

            RecipeCompleteIngredientGrid(
                modifier = Modifier
                    .weight(1f),
                ingredientList = ingredientList,
                onClick = onIngredientItemClicked
            )
        }
    }
}

@Composable
fun RecipeCompleteIngredientGrid(
    modifier: Modifier = Modifier,
    ingredientList: List<IngredientItem>,
    onClick: (Int) -> Unit
) {
    // 한 화면에 몇개의 아이템 배치할지 정하는 변수
    val horizontalCnt = 3

    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        itemsIndexed(
            items = ingredientList,
            key = { _, item ->
                item.ingredientId
            }
        ) { _, item ->
            RecipeCompleteIngredientItem(
                modifier = Modifier
                    .width((widthDp.dp - 80.dp - 10.dp * (horizontalCnt - 1)) / horizontalCnt),
                item = item,
                onClick = {
                    onClick(item.ingredientId)
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCompleteIngredientItem(
    modifier: Modifier = Modifier,
    item: IngredientItem,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .height(ScreenSizeUtil.heightDp.toDpSize(10)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick(item.ingredientId) }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .padding(top = 10.dp, bottom = 5.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    model = item.image,
                    contentDescription = stringResource(id = R.string.description_ingredient_img),
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.drawable.add_circle),
                    failure = placeholder(R.drawable.add_circle)
                )

                Text(
                    text = item.name,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            Image(
                modifier = Modifier
                    .padding(2.dp)
                    .size(15.dp)
                    .align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.close),
                contentDescription = stringResource(id = R.string.description_icon_remove),
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )
        }
    }
}