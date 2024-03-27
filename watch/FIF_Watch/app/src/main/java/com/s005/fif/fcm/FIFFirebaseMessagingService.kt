package com.s005.fif.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.s005.fif.R
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.fcm.dto.FCMIngredientDTO
import com.s005.fif.fcm.dto.FCMRecipeDataDTO
import com.s005.fif.fcm.dto.FCMStepDTO
import com.s005.fif.fcm.dto.toRecipeData
import com.s005.fif.utils.NotificationUtil.showIngredientWarningNotification
import com.s005.fif.utils.NotificationUtil.showMainActivityNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class FIFFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var fifPreferenceModule: FIFPreferenceModule

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("로그", "MyFirebaseMessagingService - onNewToken() 호출됨 ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        when (message.data["type"]!!.toInt()) {
            1 -> { // 위험 식재료 알림
                val ingredient = Json.decodeFromString<FCMIngredientDTO>(message.data["json"]!!)

                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 위험 식재료 알림 : ${ingredient}")

                showIngredientWarningNotification(this, this.getString(R.string.text_danger_ingredient), ingredient.description, ingredient.name)
            }
            2 -> { // 패널과 연동 알림
                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 패널과 연동 알림 - ${message.data["json"]!!}")
                val recipeData = Json.decodeFromString<FCMRecipeDataDTO>(message.data["json"]!!).toRecipeData()

                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 패널과 연동 알림 - recipeData : ${recipeData}")

                CoroutineScope(Dispatchers.IO).launch {
                    fifPreferenceModule.setRecipeData(recipeData)
                    RecipeLiveData.recipeData = recipeData
                    RecipeLiveData.isRecipeConnected.postValue(true)

                    showMainActivityNotification(this@FIFFirebaseMessagingService)
                }
            }
            3 -> { // 패널과 연동 해제 알림
                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 패널과 연동 해제 알림")

                CoroutineScope(Dispatchers.IO).launch {
                    fifPreferenceModule.removeRecipeData()
                    RecipeLiveData.recipeData = null
                    RecipeLiveData.isRecipeConnected.postValue(false)

                    showMainActivityNotification(this@FIFFirebaseMessagingService)
                }
            }
            4 -> { // 레시피 단계 이동 알림
                val step = Json.decodeFromString<FCMStepDTO>(message.data["json"]!!)

                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 레시피 단계 이동 알림 : ${step.step}")
                RecipeLiveData.isFcmNotification = true
                RecipeLiveData.recipeStep.postValue(step.step - 1)
            }
        }
    }
}