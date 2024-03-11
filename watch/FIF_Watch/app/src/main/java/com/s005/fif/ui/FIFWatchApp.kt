package com.s005.fif.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.s005.fif.navigation.FIFWatchNavHost

@Composable
fun FIFWatchApp(
    navController: NavHostController = rememberSwipeDismissableNavController(),
) {
    FIFWatchNavHost(
        navController = navController
    )
}