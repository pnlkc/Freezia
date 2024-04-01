package com.s005.fif.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.s005.fif.R
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.ui.theme.Typography
import com.s005.fif.user.ui.UserViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    mainViewModel: MainViewModel,
    navigateToMain: () -> Unit,
    navigateToUserSelect: () -> Unit
) {
    LaunchedEffect(true) {
        if (userViewModel.isLoginUser()) {
            userViewModel.getMemberInfo()

            if (userViewModel.memberInfo!!.onboardYn) {
                mainViewModel.getRecommendRecipeList()
                navigateToMain()
            } else {
                navigateToUserSelect()
            }
        } else {
            navigateToUserSelect()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO. 앱 아이콘으로 이미지 변경 필요
        Image(
            modifier = Modifier
                .size(100.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.app_name),
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}