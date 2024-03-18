package com.s005.fif.user.ui.profile

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography
import com.s005.fif.utils.AnnotatedStringUtil

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navigateToRecipePreferenceSetting: () -> Unit,
    navigateUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(colorScheme.background)
    ) {
        UserProfileTopBar(
            navigateUp = navigateUp
        )

        UserProfileBody(
            navigateToRecipePreferenceSetting = navigateToRecipePreferenceSetting
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfileTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
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
                    .clickable { },
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
                model = "https://img.danawa.com/prod_img/500000/207/533/img/18533207_1.jpg?_v=20221226163359",
                contentDescription = stringResource(id = R.string.description_img_profile),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun UserProfileBody(
    modifier: Modifier = Modifier,
    navigateToRecipePreferenceSetting: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        UserProfileHealthColumn(
            modifier = Modifier
        )

        UserProfileRecipePreferenceColumn(
            modifier = Modifier,
            navigateToRecipePreferenceSetting = navigateToRecipePreferenceSetting
        )

        UserProfileListColumn(
            modifier = Modifier
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfileHealthColumn(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UserProfileColumnText(
            text = stringResource(id = R.string.text_health_title, "김싸피")
        )

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
                            model = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/ts/2019/03/08/598316_280802_2053.jpg",
                            contentDescription = stringResource(id = R.string.description_img_profile),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                HealthGraphColumn(
                    modifier = modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun HealthGraphColumn(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_stress),
            progress = 0.5f
        )

        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_sleep),
            progress = 0.3f
        )

        HealthGraphProgressIndicator(
            text = stringResource(id = R.string.text_blood_oxygen),
            progress = 0.7f
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
    navigateToRecipePreferenceSetting: () -> Unit
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
                    list = listOf("한식", "일식", "국물 요리")
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                UserProfileRecipePreferenceRow(
                    text = stringResource(id = R.string.text_dislike_ingredient),
                    list = listOf("오이", "고수")
                )

                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(alpha = 0.1f)
                )

                UserProfileRecipePreferenceRow(
                    text = stringResource(id = R.string.text_illness),
                    list = listOf("당뇨")
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
            onClicked = {  }
        )

        UserProfileListColumnCard(
            full = stringResource(id = R.string.text_go_to_shopping_list_full),
            spot = stringResource(id = R.string.text_go_to_shopping_list),
            onClicked = {  }
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
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
        text = text,
        style = Typography.bodyLarge
    )
}