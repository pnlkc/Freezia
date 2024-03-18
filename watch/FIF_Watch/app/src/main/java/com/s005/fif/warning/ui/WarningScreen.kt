package com.s005.fif.warning.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.utils.ColorCombineTextUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import com.s005.fif.utils.VibrateUtil

@Composable
fun WarningScreen(
    modifier: Modifier = Modifier,
) {
    VibrateUtil.vibrateWarning(LocalContext.current)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = ScreenSize.screenHeightDp.toDpSize(5),
                bottom = ScreenSize.screenHeightDp.toDpSize(20)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(ScreenSize.screenHeightDp.toDpSize(25)),
                painter = painterResource(id = R.drawable.warning),
                contentDescription = "",
                tint = MaterialTheme.colors.error,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "OOO님",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = ScreenSize.screenHeightDp.toSpSize(10),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = ColorCombineTextUtil.makeWarningText("복숭아", "당뇨", LocalContext.current),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(10),
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}