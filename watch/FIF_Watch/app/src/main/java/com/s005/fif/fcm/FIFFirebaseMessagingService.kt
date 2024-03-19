package com.s005.fif.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FIFFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // TODO. token 을 서버로 전송
        Log.d("로그", "MyFirebaseMessagingService - onNewToken() 호출됨 ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // TODO. 수신한 메시지를 처리
        Log.d("로그", "MyFirebaseMessagingService - onMessageReceived() ${message.notification!!.title}")
        Log.d("로그", "MyFirebaseMessagingService - onMessageReceived() ${message.notification!!.body}")
    }

    private fun sendNotification(messageBody: String) {
    }

    companion object {
        private const val TAG = "FIFFirebaseMsgService"
    }
}