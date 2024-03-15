/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.s005.fif

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.tooling.preview.devices.WearDevices
import com.s005.fif.ui.theme.FIF_WatchTheme
import com.s005.fif.ui.FIFWatchApp
import com.s005.fif.utils.AlarmUtil.alarmManager
import com.s005.fif.utils.AlarmUtil.setAlarm
import com.s005.fif.utils.NotificationUtil
import com.s005.fif.utils.ScreenSize.screenHeightDp
import com.s005.fif.utils.ScreenSize.screenWidthDp
import com.s005.fif.utils.VibrateUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        getScreenSize(resources.displayMetrics)

        setContent {
            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    FIFWatchApp()
                }
            }
        }

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val hasPermission = alarmManager!!.canScheduleExactAlarms()

        if (!hasPermission) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val hasPermission = alarmManager.canScheduleExactAlarms()

        if (!hasPermission) {
            Toast.makeText(this, "FIF를 실행하기 위해서는 알람 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }

    // 스크린 사이즈 측정
    private fun getScreenSize(displayMetrics: DisplayMetrics) {
        screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
        screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "MainActivity - onDestroy() 호출됨")
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    FIF_WatchTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            FIFWatchApp()
        }
    }
}