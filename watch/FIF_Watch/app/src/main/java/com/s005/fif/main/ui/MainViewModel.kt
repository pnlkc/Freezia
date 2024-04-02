package com.s005.fif.main.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.di.LoginUser
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.main.data.MainRepository
import com.s005.fif.main.dto.AccessTokenResponse
import com.s005.fif.main.dto.MemberInfoResponse
import com.s005.fif.main.dto.MemberSelectRequest
import com.s005.fif.main.dto.SendFCMTokenRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val fifPreferenceModule: FIFPreferenceModule
) : ViewModel() {
    var mainUiState by mutableStateOf(MainUiState())

    init {
        viewModelScope.launch {
            val accessToken = runBlocking {
                fifPreferenceModule.accessTokenFlow.first()
            }

            if (accessToken == null) {
                getAccessToken()
            } else {
                Log.d("로그", "MainViewModel - init() 호출됨 / accessToken : ${accessToken}")
            }

            sendFCMToken()

            getMemberInfo()
        }
    }

    private suspend fun getAccessToken() {
        val responseResult: Response<AccessTokenResponse> = mainRepository.getAccessToken(
            MemberSelectRequest(LoginUser.memberId)
        )

        if (responseResult.isSuccessful) {
            val accessToken = responseResult.body()!!.accessToken

            Log.d("로그", "MainViewModel - getAccessToken() 호출됨 / 응답 성공 : $accessToken")

            fifPreferenceModule.setAccessToken(accessToken)
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "MainViewModel - getAccessToken() 호출됨 / 응답 실패 : ${body}")
        }
    }

    private suspend fun getMemberInfo() {
        val responseResult: Response<MemberInfoResponse> = mainRepository.getMemberInfo()

        if (responseResult.isSuccessful) {
            mainUiState = MainUiState(member = responseResult.body()!!.member.toMember())
            Log.d("로그", "MainViewModel - getMemberInfo() 호출됨 / ${mainUiState.member}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "MainViewModel - getMemberInfo() 호출됨 / 응답 실패 : ${body}")
        }
    }

   fun sendFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("로그", "MainViewModel - sendFCMToken() 호출됨 / 토큰 가져오기 실패")
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("로그", "MainViewModel - sendFCMToken() 호출됨 / 토큰 가져오기 성공 ${token}")

            viewModelScope.launch {
                val responseResult = mainRepository.sendFCMToken(
                    SendFCMTokenRequest(2, token)
                )

                if (responseResult.isSuccessful) {
                    Log.d("로그", "MainViewModel - sendFCMToken() 호출됨 / 응답 성공 : ${responseResult.body()!!}")
                } else {
                    val body = Json.decodeFromString<ErrorResponse>(
                        responseResult.errorBody()?.string()!!
                    )

                    Log.d("로그", "MainViewModel - sendFCMToken() 호출됨 / 응답 실패 : ${body}")
                }
            }
        })
    }
}