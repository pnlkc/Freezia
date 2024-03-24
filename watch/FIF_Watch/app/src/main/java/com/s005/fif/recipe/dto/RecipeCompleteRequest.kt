package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeCompleteRequest(
    val addIngredients: List<CompleteIngredientRequest>,
    val removeIngredient: List<CompleteIngredientRequest>,
    val memo: String
)
