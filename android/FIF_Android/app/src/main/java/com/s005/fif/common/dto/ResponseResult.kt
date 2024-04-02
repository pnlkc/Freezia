package com.s005.fif.common.dto

import com.s005.fif.di.AUTHORIZATION
import kotlinx.serialization.json.Json
import retrofit2.Response

// Response에 대한 결과를 담는 클래스
data class ResponseResult(
    var isSuccess: Boolean = true,
    var statusCode: Int = 0,
    var body: ErrorResponse? = null,
    var header: MutableMap<String, String> = mutableMapOf()
)

// Retrofit 응답 결과에 따른 처리 메소드
fun Response<ErrorResponse>.check(): ResponseResult {
    val responseResult = ResponseResult(isSuccess = isSuccessful, statusCode = code())

    val accessToken = headers()[AUTHORIZATION]

    if (accessToken != null) {
        responseResult.header[AUTHORIZATION] = accessToken
    }

    if (!this.isSuccessful) {
        val body = errorBody()?.string()
        responseResult.body = Json.decodeFromString<ErrorResponse>(body!!)
    }

    return responseResult
}
