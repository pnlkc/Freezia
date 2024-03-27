package com.s005.fif.fcm.dto

import com.s005.fif.fcm.RecipeData
import com.s005.fif.recipe.dto.RecipeInfoResponse
import com.s005.fif.recipe.dto.RecipeStepItemResponse
import kotlinx.serialization.Serializable

@Serializable
data class FCMRecipeDataDTO(
    val type: Int,
    val recipeInfo: RecipeInfoResponse,
    val recipeSteps: List<RecipeStepItemResponse>
)

fun FCMRecipeDataDTO.toRecipeData() = RecipeData(
    recipeInfo, recipeSteps
)