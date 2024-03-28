package com.s005.fif.warning.ui

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import com.s005.fif.main.ui.MainViewModel
import com.s005.fif.ui.theme.FIF_WatchTheme
import com.s005.fif.utils.ScreenSize.screenHeightDp
import com.s005.fif.utils.ScreenSize.screenWidthDp
import com.s005.fif.utils.VibrateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WarningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Log.d("로그", "WarningActivity - onCreate() 호출됨")

        setTheme(android.R.style.Theme_DeviceDefault)

        getScreenSize(resources.displayMetrics)

        VibrateUtil.vibrateWarning(this)

        val fullText = intent.getStringExtra("fullText") ?: ""
        val ingredient = intent.getStringExtra("ingredient") ?: ""

        setContent {
            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    WarningScreen(
                        fullText = fullText,
                        ingredient = ingredient
                    )
                }
            }
        }
    }

    // 스크린 사이즈 측정
    private fun getScreenSize(displayMetrics: DisplayMetrics) {
        screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
        screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    }

    override fun onStop() {
        super.onStop()
        VibrateUtil.cancel()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        VibrateUtil.vibrateWarning(this)

        val fullText = intent.getStringExtra("fullText") ?: ""
        val ingredient = intent.getStringExtra("ingredient") ?: ""

        setContent {
            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    WarningScreen(
                        ingredient = ingredient,
                        fullText = fullText
                    )
                }
            }
        }
    }
}