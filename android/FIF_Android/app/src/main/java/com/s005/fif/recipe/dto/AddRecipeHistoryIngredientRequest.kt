package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeHistoryIngredientRequest(
    val name: String
)
