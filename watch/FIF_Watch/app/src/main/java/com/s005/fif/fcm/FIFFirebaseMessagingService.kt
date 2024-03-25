package com.s005.fif.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.utils.NotificationUtil.showIngredientWarningNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FIFFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var fifPreferenceModule: FIFPreferenceModule

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("로그", "MyFirebaseMessagingService - onNewToken() 호출됨 ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("로그", "MyFirebaseMessagingService - onMessageReceived() ${message.notification!!.title}")
        Log.d("로그", "MyFirebaseMessagingService - onMessageReceived() ${message.notification!!.body}")

        when (message.data["type"]!!.toInt()) {
            1 -> { // 위험 식재료 알림
                val title = message.notification?.title ?: ""
                val content = message.notification?.body ?: ""
                val ingredient = message.data["name"] ?: ""

                showIngredientWarningNotification(this, title, content, ingredient)
            }
            2 -> { // 패널과 연동 알림
                val recipeInfo = Json.decodeFromString<RecipeInfo>(message.data["recipeInfo"]!!)
                val recipeSteps = Json.decodeFromString<List<RecipeStep>>(message.data["recipeSteps"]!!)
                val recipeData = RecipeData(recipeInfo, recipeSteps)

                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / recipeData : ${recipeData}")

                CoroutineScope(Dispatchers.IO).launch {
                    fifPreferenceModule.setRecipeData(recipeData)
                    RecipeLiveData.recipeData = recipeData
                    RecipeLiveData.isRecipeConnected.postValue(true)
                }
            }
            3 -> { // 패널과 연동 해제 알림
                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 패널과 연동 해제 알림")

                CoroutineScope(Dispatchers.IO).launch {
                    fifPreferenceModule.removeRecipeData()
                    RecipeLiveData.recipeData = null
                    RecipeLiveData.isRecipeConnected.postValue(false)
                }
            }
            4 -> { // 레시피 단계 이동 알림
                Log.d("로그", "FIFFirebaseMessagingService - onMessageReceived() 호출됨 / 레시피 단계 이동 알림")

                val step = message.data["step"]?.toInt() ?: 1

                RecipeLiveData.recipeStep.postValue(step)
            }
        }

    }

    companion object {
        private const val TAG = "FIFFirebaseMsgService"
    }
}