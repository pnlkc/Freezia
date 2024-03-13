package com.s005.fif.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.s005.fif.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    imgUrl: String = "https://recipe1.ezmember.co.kr/cache/recipe/2020/03/09/03ad3def1a2fd41666413d41c27d93a41.png"
) {
    GlideImage(
        modifier = modifier
            .fillMaxSize(),
        model = imgUrl,
        contentDescription = stringResource(id = R.string.background_image),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.7f), blendMode = BlendMode.Hardlight)
    )
}