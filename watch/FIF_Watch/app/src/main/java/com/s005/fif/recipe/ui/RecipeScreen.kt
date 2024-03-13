package com.s005.fif.recipe.ui

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.s005.fif.components.BackgroundImage
import com.s005.fif.utils.ColorCombineTextUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    navigateToRecipeDetail: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        BackgroundImage()

        RecipeBody(
            modifier = modifier,
            navigateToRecipeDetail = navigateToRecipeDetail
        )
    }
}

@Composable
fun RecipeBody(
    modifier: Modifier = Modifier,
    navigateToRecipeDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = ScreenSize.screenHeightDp.toDpSize(5),
                vertical = ScreenSize.screenHeightDp.toDpSize(2)
            ),
        verticalArrangement = spacedBy(
            space = ScreenSize.screenHeightDp.toDpSize(0),
            alignment = Alignment.CenterVertically
        ),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "스팸 마요 김치 덮밥",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(9),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Chip(
            modifier = Modifier
                .padding(
                    horizontal = ScreenSize.screenHeightDp.toDpSize(5),
                    vertical = ScreenSize.screenHeightDp.toDpSize(8)
                )
                .fillMaxWidth()
                .height(ScreenSize.screenHeightDp.toDpSize(22)),
            label = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "레시피 시작하기",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = ScreenSize.screenHeightDp.toSpSize(8),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = ChipDefaults.primaryChipColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            ),
            onClick = { navigateToRecipeDetail() },
            shape = MaterialTheme.shapes.large
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = ColorCombineTextUtil.makeCookTimeText(10, LocalContext.current),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(7),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}