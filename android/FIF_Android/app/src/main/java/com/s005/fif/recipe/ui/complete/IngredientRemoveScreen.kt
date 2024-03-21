package com.s005.fif.recipe.ui.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.recipe.ui.step.RecipeStepTopBar
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.onboarding.DislikeIngredientSearchResultItem
import com.s005.fif.user.ui.onboarding.DislikeIngredientSelectLazyRow
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn
import com.s005.fif.user.ui.onboarding.UserOnboardingPageTitle
import com.s005.fif.user.ui.onboarding.UserProfileTextField

@Composable
fun IngredientRemoveScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
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
        )
    }


}

@Composable
fun IngredientRemoveScreen(
    modifier: Modifier = Modifier,
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
                items = listOf<String>(
                    "파",
                    "양파",
                    "쪽파"
                ),
                key = { _, item ->
                    item
                }
            ) { _, item ->
                IngredientRemoveItem(
                    item = item
                )
            }
        }

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = {  },
            text = stringResource(id = R.string.text_btn_complete)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IngredientRemoveItem(
    modifier: Modifier = Modifier,
    item: String
) {
    val (checkedState, onStateChange) = remember { mutableStateOf(false) }

    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .toggleable(
                value = checkedState,
                onValueChange = { onStateChange(!checkedState) },
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
                model = "https://ouch-cdn2.icons8.com/-huiQFwzs0evgWutGwwsvzKk6k5OwM21IwK9pLPTF7s/rs:fit:368:412/czM6Ly9pY29uczgu/b3VjaC1wcm9kLmFz/c2V0cy9wbmcvMTky/L2I4YzI0NmMzLTA3/ZmEtNDFiOC1iMDM1/LTUyNDgyMmMxOTg4/OC5wbmc.png",
                contentDescription = stringResource(id = R.string.description_ingredient_img),
            )

            Text(
                text = item,
                style = Typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }

        Checkbox(
            checked = checkedState,
            onCheckedChange = null, // null recommended for accessibility with screenreaders
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}