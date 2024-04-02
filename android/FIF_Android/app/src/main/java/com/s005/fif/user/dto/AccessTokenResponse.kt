package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val result: String,
    val accessToken: String
)
