package com.s005.fif.user.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.s005.fif.common.data.DiseaseItemData
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.common.data.LikeFoodItemData
import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.di.FIFPreferenceModule
import com.s005.fif.di.LoginUser
import com.s005.fif.di.LoginUser.fridgeId
import com.s005.fif.user.data.UserRepository
import com.s005.fif.user.dto.AccessTokenResponse
import com.s005.fif.user.dto.Member
import com.s005.fif.user.dto.MemberInfoResponse
import com.s005.fif.user.dto.MemberSelectRequest
import com.s005.fif.user.dto.Onboarding
import com.s005.fif.user.dto.OnboardingRequest
import com.s005.fif.user.dto.UserItem
import com.s005.fif.user.dto.toMember
import com.s005.fif.user.dto.toOnboardingRequest
import com.s005.fif.user.dto.toUserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val fifPreferenceModule: FIFPreferenceModule
) : ViewModel() {
    var userList by mutableStateOf<List<UserItem>>(listOf())
    var memberInfo by mutableStateOf<Member?>(null)
    var onboardingState by mutableStateOf(Onboarding())
    var dislikeInputText by mutableStateOf("")
    var diseaseInputText by mutableStateOf("")

    init {
        viewModelScope.launch {
            getUserList()
        }
    }

    suspend fun isLoginUser(): Boolean {
        val accessToken = runBlocking {
            fifPreferenceModule.accessTokenFlow.first()
        }

        val memberId = runBlocking {
            fifPreferenceModule.memberIdFlow.first()
        }

        return if (accessToken != null && memberId != null) {
            LoginUser.memberId = memberId

            true
        } else {
            false
        }
    }

    private suspend fun getUserList() {
        val responseResult =userRepository.getUserList(fridgeId = fridgeId)

        if (responseResult.isSuccessful) {
            userList = responseResult.body()!!.memberList.map { it.toUserItem() }

            Log.d("로그", "UserViewModel - getUserList() 호출됨 / 응답 성공 : ${userList}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "UserViewModel - getUserList() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun getAccessToken(memberId: Int) {
        val responseResult: Response<AccessTokenResponse> = userRepository.getAccessToken(
            MemberSelectRequest(memberId)
        )

        if (responseResult.isSuccessful) {
            val accessToken = responseResult.body()!!.accessToken

            Log.d("로그", "UserViewModel - getAccessToken() 호출됨 / 응답 성공 : $accessToken")

            fifPreferenceModule.setAccessToken(accessToken)
            fifPreferenceModule.setMemberId(memberId)

            getMemberInfo()
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "UserViewModel - getAccessToken() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun getMemberInfo() {
        val responseResult: Response<MemberInfoResponse> = userRepository.getMemberInfo()

        if (responseResult.isSuccessful) {
            memberInfo = responseResult.body()!!.member.toMember()

            Log.d("로그", "UserViewModel - getMemberInfo() 호출됨 / 응답 성공 : ${memberInfo}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "UserViewModel - getMemberInfo() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun sendOnboarding() {
        val responseResult: Response<DefaultResponse> = userRepository.sendOnboarding(onboardingState.toOnboardingRequest())

        if (responseResult.isSuccessful) {
            Log.d("로그", "UserViewModel - sendOnboarding() 호출됨 / 응답 성공")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "UserViewModel - sendOnboarding() 호출됨 / 응답 실패 : ${body}")
        }
    }

    fun checkLikeFood(isChecked: Boolean, item: LikeFoodItemData) {
        onboardingState = if (isChecked) {
            onboardingState.copy(
                preferMenu = onboardingState.preferMenu + item
            )
        } else {
            onboardingState.copy(
                preferMenu = onboardingState.preferMenu.filter { it != item }
            )
        }

        Log.d("로그", "UserViewModel - checkLikeFood() 호출됨 / ${onboardingState.preferMenu}")
    }

    fun clearOnboarding() {
        onboardingState = Onboarding()
    }

    fun clickDislikeIngredientItem(isAdd: Boolean, item: IngredientItemData) {
        onboardingState = if (isAdd) {
            if (!onboardingState.dislikeIngredients.contains(item)) {
                onboardingState.copy(
                    dislikeIngredients = onboardingState.dislikeIngredients + item
                )
            } else {
                onboardingState
            }
        } else {
            onboardingState.copy(
                dislikeIngredients = onboardingState.dislikeIngredients.filter { it != item }
            )
        }

        Log.d("로그", "UserViewModel - clickDislikeIngredientItem() 호출됨 / ${onboardingState.dislikeIngredients}")
    }

    fun clickDiseaseItem(isAdd: Boolean, item: DiseaseItemData) {
        onboardingState = if (isAdd) {
            if (!onboardingState.diseases.contains(item)) {
                onboardingState.copy(
                    diseases = onboardingState.diseases + item
                )
            } else {
                onboardingState
            }
        } else {
            onboardingState.copy(
                diseases = onboardingState.diseases.filter { it != item }
            )
        }

        Log.d("로그", "UserViewModel - clickDiseaseItem() 호출됨 / ${onboardingState.diseases}")
    }
}