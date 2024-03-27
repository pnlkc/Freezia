package com.s005.fif.fcm.dto

import kotlinx.serialization.Serializable

@Serializable
data class FCMStepDTO(
    val type: Int,
    val step: Int
)
