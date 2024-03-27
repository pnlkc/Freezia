package com.s005.fif.fcm.dto

import kotlinx.serialization.Serializable

@Serializable
data class FCMIngredientDTO(
    val type: Int,
    val name: String,
    val imgUrl: String,
    val description: String
)
