package com.s005.fif.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse(
    val result: String,
    val message: String = ""
)
