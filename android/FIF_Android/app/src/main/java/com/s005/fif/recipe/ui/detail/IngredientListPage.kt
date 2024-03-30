package com.s005.fif.recipe.ui.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.GCDUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IngredientListPage(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel,
    recipe: RecipeListItem?,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            IngredientListHeader(
                servings = recipeViewModel.servings,
                onBtnClicked = { isAdd ->
                    recipeViewModel.changeServings(isAdd)
                }
            )
        }

        item {
            Box(modifier = Modifier.size(10.dp))
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background),
                text = stringResource(id = R.string.text_ingredient),
                style = Typography.bodyMedium
            )
        }

        itemsIndexed(
            items = recipe?.ingredientList ?: listOf(),
            key = { _, item ->
                item.ingredientId
            }
        ) { _, item ->
            IngredientListItem(
                item = item,
                servings = recipeViewModel.servings
            )
        }

        item {
            Box(modifier = Modifier.size(10.dp))
        }

        stickyHeader {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background),
                text = stringResource(id = R.string.text_seasoning),
                style = Typography.bodyMedium
            )
        }

        itemsIndexed(
            items = recipe?.seasoningList ?: listOf(),
            key = { _, item ->
                item.ingredientId
            }
        ) { _, item ->
            IngredientListItem(
                item = item,
                servings = recipeViewModel.servings
            )
        }
    }
}

@Composable
fun IngredientListHeader(
    modifier: Modifier = Modifier,
    servings: Int,
    onBtnClicked: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.text_serving_size),
            style = Typography.titleSmall
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { onBtnClicked(false) },
                painter = painterResource(id = R.drawable.remove_circle),
                contentDescription = stringResource(id = R.string.description_serving_size_decrease_btn),
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )

            Text(
                text = "${servings}인분",
                style = Typography.titleSmall
            )

            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { onBtnClicked(true) },
                painter = painterResource(id = R.drawable.add_circle),
                contentDescription = stringResource(id = R.string.description_serving_size_increase_btn),
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientListItem(
    modifier: Modifier = Modifier,
    item: IngredientItem,
    servings: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
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
                )
            }

            Text(
                text = if (item.amounts.contains("/")) {
                    var (a, b) = item.amounts.split("/").map { it.toInt() }
                    a *= servings
                    val gcd = GCDUtil.gcd(a, b)
                    a /= gcd
                    b /= gcd

                    if (b == 1) {
                        "${a.toFloat()}${item.unit}"
                    } else {
                        "${a}/${b}${item.unit}"
                    }
                } else {
                    "${item.amounts.toFloat() * servings}${item.unit}"
                },
                style = Typography.bodyMedium,
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = colorScheme.onSecondary.copy(alpha = 0.2f)
        )
    }
}