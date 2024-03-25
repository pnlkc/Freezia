package com.s005.fif.main.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.utils.AlarmUtil
import com.s005.fif.utils.NotificationUtil
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    navigateToShoppingList: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    navigateToTimerList: () -> Unit,
) {
    val btnSize = ScreenSize.screenHeightDp.toDpSize(22)
    val btnBottomPadding = ScreenSize.screenHeightDp.toDpSize(10)
    val btnSpaceBy = ScreenSize.screenWidthDp.toDpSize(2)

    // TODO. 레시피가 선택되었는지 확인 필요
    val isRecipeSelected = true

    MainBody(
        modifier = modifier,
        btnSize = btnSize,
        btnBottomPadding = btnBottomPadding,
        btnSpaceBy = btnSpaceBy,
        isRecipeSelected = isRecipeSelected,
        navigateToShoppingList = navigateToShoppingList,
        navigateToRecipe = navigateToRecipeDetail,
        navigateToTimerList = navigateToTimerList,
        mainUiState = { viewModel.mainUiState }
    )
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    btnSize: Dp,
    btnBottomPadding: Dp,
    btnSpaceBy: Dp,
    isRecipeSelected: Boolean,
    navigateToShoppingList: () -> Unit,
    navigateToRecipe: () -> Unit,
    navigateToTimerList: () -> Unit,
    mainUiState: () -> MainUiState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(ScreenSize.screenHeightDp.toDpSize(2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(11))
        ) {
            ProfileColumn(
                modifier = Modifier,
                mainUiState = mainUiState
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = if (isRecipeSelected) "알리오올리오 파스타" else stringResource(id = R.string.not_select_recipe),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = if (isRecipeSelected) ScreenSize.screenHeightDp.toSpSize(10) else ScreenSize.screenHeightDp.toSpSize(
                    8
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        MainBtnRow(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            btnSpaceBy = btnSpaceBy,
            btnBottomPadding = btnBottomPadding,
            btnSize = btnSize,
            isRecipeSelected = isRecipeSelected,
            navigateToShoppingList = navigateToShoppingList,
            navigateToRecipe = navigateToRecipe,
            navigateToTimerList = navigateToTimerList
        )
    }
}

@Composable
fun ProfileColumn(
    modifier: Modifier = Modifier,
    mainUiState: () -> MainUiState,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(
            imgUrl = mainUiState().member?.imgUrl
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(
                id = R.string.text_user_name,
                mainUiState().member?.name ?: "닉네임"
            ),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = ScreenSize.screenHeightDp.toSpSize(7.5f),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    imgUrl: String?,
) {
    if (!imgUrl.isNullOrBlank()) {
        GlideImage(
            modifier = modifier.size(ScreenSize.screenHeightDp.toDpSize(15)),
            model = imgUrl,
            contentDescription = stringResource(id = R.string.profile_img_desc),
            loading = placeholder(R.drawable.account),
            failure = placeholder(R.drawable.account)
        )
    } else {
        Icon(
            modifier = modifier.size(ScreenSize.screenHeightDp.toDpSize(15)),
            painter = painterResource(id = R.drawable.account),
            contentDescription = stringResource(id = R.string.profile_img_desc),
            tint = Color.White
        )
    }
}

@Composable
fun MainBtnRow(
    modifier: Modifier = Modifier,
    btnSpaceBy: Dp,
    btnBottomPadding: Dp,
    btnSize: Dp,
    isRecipeSelected: Boolean,
    navigateToShoppingList: () -> Unit,
    navigateToRecipe: () -> Unit,
    navigateToTimerList: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement
            .spacedBy(btnSpaceBy),
        verticalAlignment = Alignment.Bottom
    ) {

        Button(
            modifier = Modifier
                .padding(bottom = btnBottomPadding)
                .size(btnSize),
            onClick = { navigateToRecipe() },
            enabled = isRecipeSelected
        ) {
            Icon(
                modifier = Modifier
                    .background(if (isRecipeSelected) Color.White else MaterialTheme.colors.onSecondary)
                    .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                painter = painterResource(id = R.drawable.food),
                contentDescription = stringResource(id = R.string.btn_recipe_desc),
                tint = if (isRecipeSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
            )
        }


        Button(
            modifier = Modifier
                .size(btnSize),
            onClick = { navigateToShoppingList() }
        ) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = stringResource(id = R.string.btn_shopping_list_desc),
                tint = Color.White
            )
        }

        Button(
            modifier = Modifier
                .padding(bottom = btnBottomPadding)
                .size(btnSize),
            onClick = { navigateToTimerList() },
            enabled = isRecipeSelected
        ) {
            Icon(
                modifier = Modifier
                    .background(if (isRecipeSelected) Color.White else MaterialTheme.colors.onSecondary)
                    .padding(ScreenSize.screenHeightDp.toDpSize(4)),
                painter = painterResource(id = R.drawable.timer),
                contentDescription = stringResource(id = R.string.btn_timer_desc),
                tint = if (isRecipeSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
            )
        }
    }
}