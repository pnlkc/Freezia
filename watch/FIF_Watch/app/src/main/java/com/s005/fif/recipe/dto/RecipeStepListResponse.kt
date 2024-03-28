package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStepListResponse(
    val result: String,
    val recipeSteps: List<RecipeStepItemResponse>
)
