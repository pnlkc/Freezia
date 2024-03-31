/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.s005.fif.timer.ui

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import com.s005.fif.ui.theme.FIF_WatchTheme
import com.s005.fif.utils.ScreenSize.screenHeightDp
import com.s005.fif.utils.ScreenSize.screenWidthDp
import com.s005.fif.utils.VibrateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {
    private var cnt = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        getScreenSize(resources.displayMetrics)

        VibrateUtil.vibrateTimerDone(this)

        val title = intent.getStringExtra("title") ?: ""
        val initTime = intent.getIntExtra("initTime", 0)

        setContent {
            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    TimerDoneScreen(
                        text = title,
                        initTime = initTime
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

        setContent {
            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    TimerDoneScreen(
                        text = "${++cnt}개의 타이머",
                    )
                }
            }
        }
    }
}