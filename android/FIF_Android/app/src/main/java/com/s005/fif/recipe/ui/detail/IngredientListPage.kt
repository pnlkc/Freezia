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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IngredientListPage(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            IngredientListHeader()
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
            items = listOf(
                "마늘1",
                "마늘2",
                "마늘3",
                "마늘4",
                "마늘5",
                "마늘6",
                "마늘7",
                "마늘8",
                "마늘9",
                "마늘10",
                "마늘11",
                "마늘12",
                "마늘13",
                "마늘14",
                "마늘15"
            ),
            key = { _, item ->
                item
            }
        ) { _, item ->
            IngredientListItem(
                item = item
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
            items = listOf(
                "소금1",
                "소금2",
                "소금3",
                "소금4",
                "소금5",
                "소금6",
                "소금7",
                "소금8",
                "소금9",
                "소금10",
                "소금11",
                "소금12",
                "소금13",
                "소금14",
                "소금15"
            ),
            key = { _, item ->
                item
            }
        ) { _, item ->
            IngredientListItem(
                item = item
            )
        }
    }
}

@Composable
fun IngredientListHeader(
    modifier: Modifier = Modifier,
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
                    .clickable { },
                painter = painterResource(id = R.drawable.remove_circle),
                contentDescription = stringResource(id = R.string.description_serving_size_decrease_btn),
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )

            Text(
                text = "2인분",
                style = Typography.titleSmall
            )

            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { },
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
    item: String,
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
                    model = "https://ouch-cdn2.icons8.com/-huiQFwzs0evgWutGwwsvzKk6k5OwM21IwK9pLPTF7s/rs:fit:368:412/czM6Ly9pY29uczgu/b3VjaC1wcm9kLmFz/c2V0cy9wbmcvMTky/L2I4YzI0NmMzLTA3/ZmEtNDFiOC1iMDM1/LTUyNDgyMmMxOTg4/OC5wbmc.png",
                    contentDescription = stringResource(id = R.string.description_ingredient_img),
                )

                Text(
                    text = item,
                    style = Typography.bodyMedium,
                )
            }

            Text(
                text = "2개",
                style = Typography.bodyMedium,
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = colorScheme.onSecondary.copy(alpha = 0.2f)
        )
    }
}