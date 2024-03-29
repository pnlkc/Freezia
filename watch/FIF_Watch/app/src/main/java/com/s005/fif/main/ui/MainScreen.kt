package com.s005.fif.main.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.s005.fif.R
import com.s005.fif.components.BackgroundImage
import com.s005.fif.fcm.RecipeLiveData
import com.s005.fif.fridge_ingredient.ui.FridgeIngredientViewModel
import com.s005.fif.shopping_list.ui.ShoppingListViewModel
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize
import com.s005.fif.utils.ScreenSize.toSpSize
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    shoppingListViewModel: ShoppingListViewModel,
    fridgeIngredientViewModel: FridgeIngredientViewModel,
    navigateToShoppingList: () -> Unit,
    navigateToRecipeDetail: () -> Unit,
    navigateToTimerList: () -> Unit,
    navigateToFridgeIngredient: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val btnSize = ScreenSize.screenHeightDp.toDpSize(22)
    val btnBottomPadding = ScreenSize.screenHeightDp.toDpSize(17)
    val btnSpaceBy = ScreenSize.screenWidthDp.toDpSize(2)

    val isRecipeSelected = RecipeLiveData.recipeData != null

    var backWait = 0L
    val context = LocalContext.current

    BackHandler {
        if (System.currentTimeMillis() - backWait >= 2000) {
            backWait = System.currentTimeMillis()
            Toast.makeText(
                context,
                context.getText(R.string.exit_toast_message),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            (context as? Activity)?.finish()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        BackgroundImage(
            imgUrl = RecipeLiveData.recipeData?.recipeInfo?.imgUrl ?: ""
        )

        MainBody(
            modifier = Modifier,
            btnSize = btnSize,
            btnBottomPadding = btnBottomPadding,
            btnSpaceBy = btnSpaceBy,
            isRecipeSelected = isRecipeSelected,
            navigateToShoppingList = {
                coroutineScope.launch {
                    shoppingListViewModel.getShoppingList()
                    navigateToShoppingList()
                }
            },
            navigateToRecipe = navigateToRecipeDetail,
            navigateToTimerList = navigateToTimerList,
            navigateToFridgeIngredient = {
                coroutineScope.launch {
                    fridgeIngredientViewModel.getFridgeIngredientList()
                    navigateToFridgeIngredient()
                }
            },
            mainUiState = { mainViewModel.mainUiState }
        )
    }
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
    navigateToFridgeIngredient: () -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(if (isRecipeSelected) 7 else 15))
        ) {
            ProfileColumn(
                modifier = Modifier,
                mainUiState = mainUiState
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = if (isRecipeSelected) RecipeLiveData.recipeData!!.recipeInfo.name else stringResource(id = R.string.not_select_recipe),
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
                .align(Alignment.BottomCenter)
                .padding(horizontal = ScreenSize.screenWidthDp.toDpSize(5))
                .padding(bottom = ScreenSize.screenWidthDp.toDpSize(3)),
            btnSpaceBy = btnSpaceBy,
            btnBottomPadding = btnBottomPadding,
            btnSize = btnSize,
            isRecipeSelected = isRecipeSelected,
            navigateToShoppingList = navigateToShoppingList,
            navigateToRecipe = navigateToRecipe,
            navigateToTimerList = navigateToTimerList,
            navigateToFridgeIngredient = navigateToFridgeIngredient
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
            modifier = modifier
                .size(ScreenSize.screenHeightDp.toDpSize(15))
                .clip(CircleShape),
            model = imgUrl,
            contentDescription = stringResource(id = R.string.profile_img_desc),
            contentScale = ContentScale.Crop,
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
    navigateToFridgeIngredient: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        if (isRecipeSelected) {
            Button(
                modifier = Modifier
                    .padding(bottom = btnBottomPadding)
                    .size(btnSize)
                    .align(Alignment.TopStart),
                onClick = { navigateToRecipe() },
            ) {
                Icon(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                    painter = painterResource(id = R.drawable.food),
                    contentDescription = stringResource(id = R.string.btn_recipe_desc),
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(ScreenSize.screenHeightDp.toDpSize(2f))
        ) {
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
                    .size(btnSize),
                onClick = {
                    navigateToFridgeIngredient()
                }
            ) {
                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                    painter = painterResource(id = R.drawable.fridge),
                    contentDescription = stringResource(id = R.string.btn_timer_to_recipe),
                    tint = Color.White
                )
            }
        }

        if (isRecipeSelected) {
            Button(
                modifier = Modifier
                    .padding(bottom = btnBottomPadding)
                    .size(btnSize)
                    .align(Alignment.TopEnd),
                onClick = { navigateToTimerList() },
            ) {
                Icon(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(ScreenSize.screenHeightDp.toDpSize(6)),
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = stringResource(id = R.string.btn_timer_desc),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}