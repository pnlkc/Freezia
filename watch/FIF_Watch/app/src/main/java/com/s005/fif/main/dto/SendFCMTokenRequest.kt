package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendFCMTokenRequest(
    val type: Int,
    val token: String
)
