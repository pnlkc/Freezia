package com.s005.fif

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.s005.fif.ui.FIFApp
import com.s005.fif.ui.theme.FIF_AndroidTheme
import com.s005.fif.utils.ScreenSizeUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = resources.displayMetrics
        getScreenSize(displayMetrics)

        setContent {
            FIF_AndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    FIFApp()
                }
            }
        }
    }

    /**
     * 스크린 사이즈 측정
     */
    private fun getScreenSize(displayMetrics: DisplayMetrics) {
        ScreenSizeUtil.screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
        ScreenSizeUtil.screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    }
}