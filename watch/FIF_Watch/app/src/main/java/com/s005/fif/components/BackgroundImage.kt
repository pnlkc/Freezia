package com.s005.fif.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    imgUrl: String? = null,
    type: Int? = null,
) {
    if (imgUrl != null) {
        GlideImage(
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = imgUrl,
            contentDescription = stringResource(id = R.string.background_image),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.7f),
                blendMode = BlendMode.Hardlight
            )
        )
    } else {
        Image(
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painterResource(
                id = when (type) {
                    0 -> R.drawable.recipe_step_1
                    1 -> R.drawable.recipe_step_2
                    else -> R.drawable.recipe_step_3
                }
            ),
            contentDescription = stringResource(id = R.string.background_image),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.7f),
                blendMode = BlendMode.Hardlight
            )
        )
    }
}