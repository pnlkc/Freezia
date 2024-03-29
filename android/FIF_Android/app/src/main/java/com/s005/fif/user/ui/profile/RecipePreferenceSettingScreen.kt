package com.s005.fif.user.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.s005.fif.R
import com.s005.fif.common.data.DiseaseListData
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.Member
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.onboarding.UserOnboardingBtn

@Composable
fun RecipePreferenceSettingScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navigateUp: () -> Unit,
    navigateToUserOnboarding: () -> Unit,
    navigateToRecipeHistory: () -> Unit,
    navigateToUserSelect: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp,
            memberInfo = userViewModel.memberInfo,
            navigateToRecipeHistory = navigateToRecipeHistory,
            navigateToUserSelect = navigateToUserSelect
        )

        RecipePreferenceSettingBody(
            memberInfo = userViewModel.memberInfo,
            navigateToUserOnboarding = {
                userViewModel.getOnboardingData()
                navigateToUserOnboarding()
            }
        )
    }
}

@Composable
fun RecipePreferenceSettingBody(
    modifier: Modifier = Modifier,
    memberInfo: Member?,
    navigateToUserOnboarding: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RecipePreferenceSettingColumn(
            modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            memberInfo = memberInfo
        )

        UserOnboardingBtn(
            modifier = Modifier,
            onClick = navigateToUserOnboarding,
            text = stringResource(id = R.string.text_btn_edit)
        )
    }
}

@Composable
fun RecipePreferenceSettingColumn(
    modifier: Modifier = Modifier,
    memberInfo: Member?
) {
    Column(
        modifier = modifier
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            text = stringResource(id = R.string.text_recipe_preference_setting_title)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                RecipePreferenceSettingRow(
                    text = stringResource(id = R.string.text_like_food_setting_hint),
                    list = memberInfo?.preferMenu?.split(", ") ?: listOf()
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                RecipePreferenceSettingRow(
                    text = stringResource(id = R.string.text_dislike_ingredient_setting_hint),
                    list = memberInfo?.dislikeIngredients?.map { IngredientListData.map[it]!! } ?: listOf()
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                RecipePreferenceSettingRow(
                    text = stringResource(id = R.string.text_illness_setting_hint),
                    list = memberInfo?.diseases?.map { DiseaseListData.map[it]!! } ?: listOf()
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipePreferenceSettingRow(
    modifier: Modifier = Modifier,
    text: String,
    list: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = Typography.titleSmall,
            color = Color.Black
        )

        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            list.forEach { item ->
                RecipePreferenceSettingLazyRowItem(
                    item = item,
                )
            }
        }
    }
}

@Composable
fun RecipePreferenceSettingLazyRowItem(
    modifier: Modifier = Modifier,
    item: String,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(colorScheme.primary)
            .padding(vertical = 5.dp, horizontal = 10.dp),
    ) {
        Text(
            modifier = modifier
                .clip(RoundedCornerShape(50.dp))
                .background(colorScheme.primary),
            text = item,
            style = Typography.bodyMedium,
            color = colorScheme.onPrimary
        )
    }
}