package com.s005.fif.user.network

import com.s005.fif.user.dto.AccessTokenResponse
import com.s005.fif.user.dto.MemberInfoResponse
import com.s005.fif.user.dto.MemberSelectRequest
import com.s005.fif.user.dto.UserListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("members")
    suspend fun getUserList(
        @Query("fridgeId") fridgeId: Int
    ) : Response<UserListResponse>

    @POST("members")
    suspend fun getAccessToken(
        @Body memberSelectRequest: MemberSelectRequest
    ): Response<AccessTokenResponse>

    @GET("members/info")
    suspend fun getMemberInfo() : Response<MemberInfoResponse>
}