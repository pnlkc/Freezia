package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeHistoryRequest(
    val addIngredients: List<AddRecipeHistoryIngredientRequest>,
    val removeIngredients: List<AddRecipeHistoryIngredientRequest>,
    val memo: String = ""
)
