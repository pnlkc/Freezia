package com.s005.fif.recipe.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R
import com.s005.fif.ui.theme.Typography

@Composable
fun MyFoodHistoryPage(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(3.dp)
    ) {
        itemsIndexed(
            items = listOf<String>("24.03.05", "24.03.04", "24.03.03", "24.03.02", "24.03.01"),
            key = { _, item ->
                item
            }
        ) { _, item ->
            MyFoodHistoryItem(
                modifier = Modifier,
                item = item
            )
        }
    }
}

@Composable
fun MyFoodHistoryItem(
    modifier: Modifier = Modifier,
    item: String
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
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = item,
                style = Typography.titleSmall
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MyFoodHistoryIngredientList(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.text_add_ingredient),
                    img = painterResource(id = R.drawable.add_circle),
                    desc = stringResource(id = R.string.description_add_ingredient),
                    list = listOf("양배추", "버섯", "당근")
                )

                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight(),
                    thickness = 1.dp,
                    color = colorScheme.onSecondary.copy(0.2f)
                )

                MyFoodHistoryIngredientList(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.text_remove_ingredient),
                    img = painterResource(id = R.drawable.remove_circle),
                    desc = stringResource(id = R.string.description_remove_ingredient),
                    list = listOf("오이", "아스파라거스")
                )
            }
        }
    }
}

@Composable
fun MyFoodHistoryIngredientList(
    modifier: Modifier = Modifier,
    text: String,
    img: Painter,
    desc: String,
    list: List<String>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .clickable { },
                painter = img,
                contentDescription = desc,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

            Text(
                text = text,
                style = Typography.bodyMedium
            )
        }

        list.forEach { item ->
            MyFoodHistoryIngredientItem(
                item = item
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyFoodHistoryIngredientItem(
    modifier: Modifier = Modifier,
    item: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp),
            model = "https://ouch-cdn2.icons8.com/-huiQFwzs0evgWutGwwsvzKk6k5OwM21IwK9pLPTF7s/rs:fit:368:412/czM6Ly9pY29uczgu/b3VjaC1wcm9kLmFz/c2V0cy9wbmcvMTky/L2I4YzI0NmMzLTA3/ZmEtNDFiOC1iMDM1/LTUyNDgyMmMxOTg4/OC5wbmc.png",
            contentDescription = stringResource(id = R.string.description_ingredient_img),
        )

        Text(
            text = item,
            style = Typography.bodyMedium,
            maxLines = 1
        )
    }
}