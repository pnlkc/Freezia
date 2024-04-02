package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val result: String,
    val accessToken: String
)