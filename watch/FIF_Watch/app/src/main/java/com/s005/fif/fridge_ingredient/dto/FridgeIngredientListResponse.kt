package com.s005.fif.fridge_ingredient.dto

import kotlinx.serialization.Serializable

@Serializable
data class FridgeIngredientListResponse(
    val result: String,
    val fridgeIngredients: List<FridgeIngredientItemResponse>
)
