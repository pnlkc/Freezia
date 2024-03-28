package com.s005.fif.user.ui.profile

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.common.data.DiseaseListData
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.recipe.ui.detail.IngredientListItem
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.dto.Member
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.utils.AnnotatedStringUtil

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navigateToRecipePreferenceSetting: () -> Unit,
    navigateUp: () -> Unit,
    navigateToSavedRecipe: () -> Unit,
    navigationToShoppingList: () -> Unit,
    navigateToRecipeHistory: () -> Unit
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
            navigateToRecipeHistory = navigateToRecipeHistory
        )

        UserProfileBody(
            navigateToRecipePreferenceSetting = navigateToRecipePreferenceSetting,
            navigateToSavedRecipe = navigateToSavedRecipe,
            navigationToShoppingList = navigationToShoppingList,
            memberInfo = userViewModel.memberInfo
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfileTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    memberInfo: Member?,
    navigateToRecipeHistory: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .clickable { navigateUp() },
            painter = painterResource(id = R.drawable.back),
            contentDescription = stringResource(id = R.string.description_btn_go_back)
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { navigateToRecipeHistory() },
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = stringResource(id = R.string.description_btn_bookmark)
            )

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { },
                painter = painterResource(id = R.drawable.notification),
                contentDescription = stringResource(id = R.string.description_btn_notification)
            )

            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { },
                model = memberInfo?.imgUrl ?: "",
                contentDescription = stringResource(id = R.string.description_img_profile),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.account),
                failure = placeholder(R.drawable.account)
            )
        }
    }
}

@Composable
fun UserProfileBody(
    modifier: Modifier = Modifier,
    navigateToRecipePreferenceSetting: () -> Unit,
    navigateToSavedRecipe: () -> Unit,
    navigationToShoppingList: () -> Unit,
    memberInfo: Member?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        UserProfileHealthColumn(
            modifier = Modifier,
            memberInfo = memberInfo
        )

        UserProfileRecipePreferenceColumn(
            modifier = Modifier,
            navigateToRecipePreferenceSetting = navigateToRecipePreferenceSetting,
            memberInfo = memberInfo
        )

        UserProfileListColumn(
            modifier = Modifier,
            navigateToSavedRecipe = navigateToSavedRecipe,
            navigationToShoppingList = navigationToShoppingList
        )
    }
}

@Composable
fun UserProfileHealthColumn(
    modifier: Modifier = Modifier,
    memberInfo: Member?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            text = stringResource(id = R.string.text_health_title, memberInfo?.name ?: "닉네임")
        )

        HealthCard(
            memberInfo = memberInfo
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HealthCard(
    modifier: Modifier = Modifier,
    memberInfo: Member?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(top = 10.dp),
                    painter = painterResource(id = R.drawable.samsung_health),
                    contentDescription = stringResource(id = R.string.description_samsung_health)
                )

                Box(
                    modifier = modifier
                        .clip(CircleShape)
                        .size(100.dp)
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp)
                ) {
                    GlideImage(
                        modifier = modifier
                            .clip(CircleShape)
                            .size(80.dp)
                            .align(Alignment.BottomCenter),
                        model = memberInfo?.imgUrl ?: "",
                        contentDescription = stringResource(id = R.string.description_img_profile),
                        contentScale = ContentScale.Crop,
                        loading = placeholder(R.drawable.account),
                        failure = placeholder(R.drawable.account)
                    )
                }
            }

            HealthGraphColumn(
                modifier = modifier
                    .weight(1f),
                memberInfo = memberInfo
            )
        }
    }
}

@Composable
fun HealthGraphColumn(
    modifier: Modifier = Modifier,
    memberInfo: Member?
) {
    val progressAnimDuration = 1000

    val stressProgressAnimation by animateFloatAsState(
        targetValue = if (memberInfo == null) 0f else (memberInfo.stress.toFloat() / 10),
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )

    val sleepProgressAnimation by animateFloatAsState(
        targetValue = if (memberInfo == null) 0f else (memberInfo.sleep.toFloat() / 10),
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )

    val bloodOxygenProgressAnimation by animateFloatAsState(
        targetValue = if (memberInfo == null) 0f else (memberInfo.bloodOxygen.toFloat() / 100),
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_stress),
            progress = stressProgressAnimation
        )

        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_sleep),
            progress = sleepProgressAnimation
        )

        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_blood_oxygen),
            progress = bloodOxygenProgressAnimation
        )
    }
}

@Composable
fun HealthGraphProgressIndicator(
    modifier: Modifier = Modifier,
    text: String,
    progress: Float,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = text,
            style = Typography.bodySmall
        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    1.dp,
                    colorScheme.onSecondary.copy(alpha = 0.3f),
                    RoundedCornerShape(20.dp)
                ),
            progress = { progress },
            strokeCap = StrokeCap.Round,
            trackColor = Color.White,
            color = when (text) {
                stringResource(id = R.string.text_stress) -> Color(0xFFC63000)
                stringResource(id = R.string.text_sleep) -> Color(0xFF0068FE)
                else -> Color(0xFF26B103)
            }
        )
    }
}

@Composable
fun UserProfileRecipePreferenceColumn(
    modifier: Modifier = Modifier,
    navigateToRecipePreferenceSetting: () -> Unit,
    memberInfo: Member?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserProfileColumnText(
                text = stringResource(id = R.string.text_recipe_preference_title)
            )

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable { navigateToRecipePreferenceSetting() },
                painter = painterResource(id = R.drawable.setting),
                contentDescription = stringResource(id = R.string.description_btn_recipe_preference_setting)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                UserProfileRecipePreferenceRow(
                    text = stringResource(id = R.string.text_like_food),
                    list = memberInfo?.preferMenu?.split(", ") ?: listOf()
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                UserProfileRecipePreferenceRow(
                    text = stringResource(id = R.string.text_dislike_ingredient),
                    list = memberInfo?.dislikeIngredients?.map { IngredientListData.map[it]!! } ?: listOf()
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                UserProfileRecipePreferenceRow(
                    text = stringResource(id = R.string.text_illness),
                    list = memberInfo?.diseases?.map { DiseaseListData.map[it]!! } ?: listOf()
                )
            }
        }
    }
}

@Composable
fun UserProfileRecipePreferenceRow(
    modifier: Modifier = Modifier,
    text: String,
    list: List<String>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = Typography.bodySmall
        )

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(
                items = list,
                key = { _, item ->
                    item
                }
            ) { _, item ->
                UserProfileRecipePreferenceLazyRowItem(
                    item = item
                )
            }
        }
    }
}

@Composable
fun UserProfileRecipePreferenceLazyRowItem(
    modifier: Modifier = Modifier,
    item: String,
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(colorScheme.primary)
            .padding(vertical = 5.dp, horizontal = 10.dp),
        text = item,
        style = Typography.bodyMedium,
        color = colorScheme.onPrimary
    )
}

@Composable
fun UserProfileListColumn(
    modifier: Modifier = Modifier,
    navigateToSavedRecipe: () -> Unit,
    navigationToShoppingList: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            text = stringResource(id = R.string.text_list_title)
        )

        UserProfileListColumnCard(
            full = stringResource(id = R.string.text_go_to_recipe_history_full),
            spot = stringResource(id = R.string.text_go_to_recipe_history),
            onClicked = { navigateToSavedRecipe() }
        )

        UserProfileListColumnCard(
            full = stringResource(id = R.string.text_go_to_shopping_list_full),
            spot = stringResource(id = R.string.text_go_to_shopping_list),
            onClicked = { navigationToShoppingList() }
        )
    }
}

@Composable
fun UserProfileListColumnCard(
    modifier: Modifier = Modifier,
    full: String,
    spot: String,
    onClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClicked() }
                .padding(15.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = AnnotatedStringUtil.makeString(
                    full = full,
                    spot = spot
                )
            )
        }
    }
}

@Composable
fun UserProfileColumnText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = Typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
}