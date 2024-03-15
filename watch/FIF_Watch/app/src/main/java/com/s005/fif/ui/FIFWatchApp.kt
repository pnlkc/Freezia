package com.s005.fif.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

