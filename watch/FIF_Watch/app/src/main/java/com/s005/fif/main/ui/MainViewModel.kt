package com.s005.fif.main.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.di.LoginUser
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.main.data.MainRepository
import com.s005.fif.main.dto.AccessTokenResponse
import com.s005.fif.main.dto.MemberInfoResponse
import com.s005.fif.main.dto.MemberSelectRequest
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
            }

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
}