package com.s005.fif.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.s005.fif.ui.navigation.FIFNavHost

@Composable
fun FIFApp(
    navController: NavHostController = rememberNavController(),
) {
    FIFNavHost(
        navController = navController
    )
}