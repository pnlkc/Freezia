package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailResponse(
    val result: String,
    val recipeInfo: RecipeInfoResponse
)