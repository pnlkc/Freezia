package com.s005.fif.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val result: String,
    val code: Long,
    val message: String
)
