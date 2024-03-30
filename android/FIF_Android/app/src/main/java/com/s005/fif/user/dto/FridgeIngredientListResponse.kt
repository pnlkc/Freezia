package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class FridgeIngredientListResponse(
    val result: String,
    val fridgeIngredients: List<FridgeIngredientItemResponse>
)

