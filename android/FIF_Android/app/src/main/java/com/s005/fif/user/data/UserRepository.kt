package com.s005.fif.user.data

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.user.dto.AccessTokenResponse
import com.s005.fif.user.dto.MemberInfoResponse
import com.s005.fif.user.dto.MemberSelectRequest
import com.s005.fif.user.dto.OnboardingRequest
import com.s005.fif.user.dto.RecipeHistoryListResponse
import com.s005.fif.user.dto.UserListResponse
import com.s005.fif.user.network.UserService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import javax.inject.Inject

interface UserRepository {
    suspend fun getUserList(
        fridgeId: Int,
    ): Response<UserListResponse>

    suspend fun getAccessToken(
        memberSelectRequest: MemberSelectRequest,
    ): Response<AccessTokenResponse>

    suspend fun getMemberInfo(): Response<MemberInfoResponse>

    suspend fun sendOnboarding(
        onboardingRequest: OnboardingRequest,
    ): Response<DefaultResponse>

    suspend fun getSavedRecipeList(): Response<RecipeHistoryListResponse>

    suspend fun getCompletedRecipeList(): Response<RecipeHistoryListResponse>

    suspend fun editUserPreference(
        onboardingRequest: OnboardingRequest,
    ): Response<DefaultResponse>
}

class DefaultUserRepository @Inject constructor(
    private val userService: UserService,
) : UserRepository {
    override suspend fun getUserList(fridgeId: Int): Response<UserListResponse> {
        return userService.getUserList(fridgeId)
    }

    override suspend fun getAccessToken(memberSelectRequest: MemberSelectRequest): Response<AccessTokenResponse> {
        return userService.getAccessToken(memberSelectRequest)
    }

    override suspend fun getMemberInfo(): Response<MemberInfoResponse> {
        return userService.getMemberInfo()
    }

    override suspend fun sendOnboarding(onboardingRequest: OnboardingRequest): Response<DefaultResponse> {
        return userService.sendOnboarding(onboardingRequest)
    }

    override suspend fun getSavedRecipeList(): Response<RecipeHistoryListResponse> {
        return userService.getSavedRecipeList()
    }

    override suspend fun getCompletedRecipeList(): Response<RecipeHistoryListResponse> {
        return userService.getCompletedRecipeList()
    }

    override suspend fun editUserPreference(onboardingRequest: OnboardingRequest): Response<DefaultResponse> {
        return userService.editUserPreference(onboardingRequest)
    }
}