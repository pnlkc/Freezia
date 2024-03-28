package com.s005.fif.fcm.dto

import com.s005.fif.recipe.dto.RecipeInfoResponse
import com.s005.fif.recipe.dto.RecipeStepItemResponse
import com.s005.fif.recipe.dto.RecipeStepListResponse
import kotlinx.serialization.Serializable

@Serializable
data class RecipeData(
    val recipeInfo: RecipeInfoResponse,
    val recipeSteps: List<RecipeStepItemResponse>
)