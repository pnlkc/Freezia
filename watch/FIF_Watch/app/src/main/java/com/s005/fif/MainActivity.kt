/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.s005.fif

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.fcm.RecipeLiveData
import com.s005.fif.fcm.dto.FCMIngredientDTO
import com.s005.fif.fcm.dto.FCMRecipeDataDTO
import com.s005.fif.fcm.dto.FCMStepDTO
import com.s005.fif.fcm.dto.toRecipeData
import com.s005.fif.navigation.NavigationDestination
import com.s005.fif.timer.ui.TimerViewModel
import com.s005.fif.ui.FIFWatchApp
import com.s005.fif.ui.theme.FIF_WatchTheme
import com.s005.fif.utils.AlarmUtil.alarmManager
import com.s005.fif.utils.NotificationUtil
import com.s005.fif.utils.ScreenSize.screenHeightDp
import com.s005.fif.utils.ScreenSize.screenWidthDp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull.content
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val timerViewModel: TimerViewModel by viewModels()
    @Inject
    lateinit var fifPreferenceModule: FIFPreferenceModule

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 허용된 경우의 처리
            Toast.makeText(this, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // 권한이 거부된 경우의 처리
            Toast.makeText(this, "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getNotificationPermission()
        }

        getScreenSize(resources.displayMetrics)

        getFCMToken()

        getRecipe()

        setContent {
            val navController = rememberSwipeDismissableNavController()
            val timerViewModel: TimerViewModel = hiltViewModel()

            FIF_WatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    FIFWatchApp(
                        navController = navController,
                        timerViewModel = timerViewModel
                    )
                }
            }

            RecipeLiveData.isRecipeConnected.observe(this) { isRecipeConnected ->
                if (isRecipeConnected) {
                    navController.navigate(NavigationDestination.RecipeDetail.route) {
                        launchSingleTop = true
                    }
                } else {
                    timerViewModel.deleteTimerListDataStore()

                    navController.navigate(NavigationDestination.Main.route) {
                        launchSingleTop = true
                    }
                }
            }

            RecipeLiveData.recipeStep.observe(this) { step ->
                if (RecipeLiveData.isRecipeConnected.value!! && step != -1 && RecipeLiveData.isFcmNotification) {
                    navController.navigate("${NavigationDestination.RecipeStep.route}/$step") {
                        launchSingleTop = true
                    }
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getNotificationPermission() {
        // 권한을 체크하고 없는 경우 요청합니다.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // POST_NOTIFICATIONS 권한을 요청합니다.
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            // 이미 권한이 있는 경우에 대한 처리
            Toast.makeText(this, "이미 알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
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


    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("로그", "MainActivity - getFCMToken() 호출됨 / 토큰 가져오기 실패")
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("로그", "MainActivity - getFCMToken() 호출됨 / 토큰 가져오기 성공 ${token}")
        })
    }

    private fun getRecipe() {
        val recipeData = runBlocking {
            fifPreferenceModule.recipeDataFlow.first()
        }

        if (recipeData != null) {
            RecipeLiveData.recipeData = recipeData
            RecipeLiveData.isRecipeConnected.value = true
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val type = intent?.getStringExtra("type")?.toInt() ?: 0
        val json = intent?.getStringExtra("json")

        when (type) {
            4 -> { // 레시피 단계 이동 알림
                val step = Json.decodeFromString<FCMStepDTO>(json!!)

                RecipeLiveData.isFcmNotification = true
                RecipeLiveData.recipeStep.postValue(step.step - 1)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        timerViewModel.getTimerList()
    }

    override fun onStop() {
        super.onStop()

        timerViewModel.saveTimerListDataStore()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "MainActivity - onDestroy() 호출됨")
    }
}