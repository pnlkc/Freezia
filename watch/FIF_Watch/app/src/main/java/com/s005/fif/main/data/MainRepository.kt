package com.s005.fif.main.data

import com.s005.fif.main.dto.AccessTokenResponse
import com.s005.fif.main.dto.MemberInfoResponse
import com.s005.fif.main.dto.MemberSelectRequest
import com.s005.fif.main.network.MainService
import retrofit2.Response
import javax.inject.Inject

interface MainRepository {
    suspend fun getAccessToken(memberSelectRequest: MemberSelectRequest): Response<AccessTokenResponse>

    suspend fun getMemberInfo(): Response<MemberInfoResponse>
}

class DefaultMainRepository @Inject constructor(
    private val mainService: MainService
) : MainRepository {
    override suspend fun getAccessToken(memberSelectRequest: MemberSelectRequest): Response<AccessTokenResponse> {
        return mainService.getAccessToken(memberSelectRequest)
    }

    override suspend fun getMemberInfo(): Response<MemberInfoResponse> {
        return mainService.getMemberInfo()
    }

}