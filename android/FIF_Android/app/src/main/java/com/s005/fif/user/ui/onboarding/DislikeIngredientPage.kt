package com.s005.fif.user.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography

@Composable
fun DislikeIngredientPage(
    modifier: Modifier = Modifier,
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
        goNextPage = goNextPage
    )
}

@Composable
fun DislikeIngredientBody(
    modifier: Modifier = Modifier,
    goPrevPage: () -> Unit,
    goNextPage: () -> Unit,
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
                    content = "",
                    setContent = { },
                    hintText = stringResource(id = R.string.text_field_hint_dislike_ingredient)
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
                DislikeIngredientSearchResultItem(
                    item = item
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

        DislikeIngredientSelectLazyRow()

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
    item: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {  },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(20.dp)
                .border(1.dp, Color.Black, CircleShape)
        ) {
            Image(
                modifier = modifier
                    .size(20.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.add),
                contentDescription = stringResource(id = R.string.description_icon_add),
                colorFilter = ColorFilter.tint(Color.Black),
                contentScale = ContentScale.Fit
            )
        }

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
}

@Composable
fun DislikeIngredientSelectLazyRow(
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
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
            DislikeIngredientSelectItem(
                modifier = Modifier,
                item = item,
                onClick = {  }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DislikeIngredientSelectItem(
    modifier: Modifier = Modifier,
    item: String,
    onClick: () -> Unit
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
                .clickable { onClick() }
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GlideImage(
                modifier = modifier
                    .clip(CircleShape)
                    .size(25.dp),
                model = "https://ouch-cdn2.icons8.com/-huiQFwzs0evgWutGwwsvzKk6k5OwM21IwK9pLPTF7s/rs:fit:368:412/czM6Ly9pY29uczgu/b3VjaC1wcm9kLmFz/c2V0cy9wbmcvMTky/L2I4YzI0NmMzLTA3/ZmEtNDFiOC1iMDM1/LTUyNDgyMmMxOTg4/OC5wbmc.png",
                contentDescription = stringResource(id = R.string.description_ingredient_img),
            )

            Text(
                text = item,
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