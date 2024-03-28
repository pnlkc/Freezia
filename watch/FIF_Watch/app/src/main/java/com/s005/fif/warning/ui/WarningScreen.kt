package com.s005.fif.warning.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.main.ui.MainViewModel
import com.s005.fif.utils.ColorCombineTextUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize

@Composable
fun WarningScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    ingredient: String = "",
    fullText: String = ""
) {
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
                    .size(ScreenSize.screenHeightDp.toDpSize(25))
                    .background(MaterialTheme.colors.error)
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.warning),
                contentDescription = stringResource(id = R.string.description_warning_ingredient_icon),
                tint = Color.White,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${mainViewModel.mainUiState.member?.name ?: "닉네임"}님",
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
                .fillMaxWidth()
                .padding(horizontal = ScreenSize.screenWidthDp.toDpSize(10)),
            text = ColorCombineTextUtil.makeWarningText(ingredient, stringResource(id = R.string.text_danger_ingredient_body, ingredient)),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(10),
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}