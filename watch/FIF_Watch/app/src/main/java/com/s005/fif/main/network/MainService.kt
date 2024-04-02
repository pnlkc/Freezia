package com.s005.fif.main.network

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.main.dto.AccessTokenResponse
import com.s005.fif.main.dto.MemberInfoResponse
import com.s005.fif.main.dto.MemberSelectRequest
import com.s005.fif.main.dto.SendFCMTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainService {
    @POST("members")
    suspend fun getAccessToken(
        @Body memberSelectRequest: MemberSelectRequest
    ): Response<AccessTokenResponse>

    @GET("members/info")
    suspend fun getMemberInfo() : Response<MemberInfoResponse>

    @POST("fcm/token")
    suspend fun sendFCMToken(
        @Body sendFCMTokenRequest: SendFCMTokenRequest
    ): Response<DefaultResponse>
}