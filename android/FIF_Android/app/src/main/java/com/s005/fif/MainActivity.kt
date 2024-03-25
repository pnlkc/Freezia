package com.s005.fif

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.di.LoginUser
import com.s005.fif.ui.FIFApp
import com.s005.fif.ui.theme.FIF_AndroidTheme
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.utils.ScreenSizeUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var fifPreferenceModule: FIFPreferenceModule
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        getScreenSize(resources.displayMetrics)

        setContent {
            FIF_AndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    FIFApp(
                        isLoginUser = isLoginUser()
                    )
                }
            }
        }
    }

    // 상태바 높이 측정
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    private fun getStatusBarHeight(displayMetrics: DisplayMetrics): Int {
        var result = 0
        val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {
            result = this.resources.getDimensionPixelSize(resourceId)
        }

        ScreenSizeUtil.statusBarHeightDp = (result / displayMetrics.density)

        return result
    }

    // 스크린 사이즈 측정
    private fun getScreenSize(displayMetrics: DisplayMetrics) {
        ScreenSizeUtil.heightDp = (displayMetrics.heightPixels - getStatusBarHeight(displayMetrics)) / displayMetrics.density
        ScreenSizeUtil.widthDp = displayMetrics.widthPixels / displayMetrics.density
    }

    private fun isLoginUser(): Boolean {
        val accessToken = runBlocking {
            fifPreferenceModule.accessTokenFlow.first()
        }

        val memberId = runBlocking {
            fifPreferenceModule.memberIdFlow.first()
        }

        return if (accessToken != null && memberId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.getMemberInfo()
            }

            LoginUser.memberId = memberId

            true
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                userViewModel.getUserList()
            }

            false
        }
    }
}