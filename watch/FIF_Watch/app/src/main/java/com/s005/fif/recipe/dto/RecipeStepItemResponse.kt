package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStepItemResponse(
    val recipeStepId: Int,
    val stepNumber: Int,
    val description: String,
    val descriptionWatch: String,
    val type: Int,
    val tip: String,
    val timer: Int?
)
