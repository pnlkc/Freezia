package com.s005.fif.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.s005.fif.R
import com.s005.fif.user.ui.UserViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
    navigateToUserSelect: () -> Unit
) {
    LaunchedEffect(true) {
        if (userViewModel.isLoginUser()) {
            userViewModel.getMemberInfo()

            if (userViewModel.memberInfo!!.onboardYn) {
                navigateToMain()
            } else {
                navigateToUserSelect()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.account),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.Crop
        )
    }
}