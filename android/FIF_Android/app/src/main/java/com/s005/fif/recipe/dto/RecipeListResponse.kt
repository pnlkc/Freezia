package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeListResponse(
    val result: String,
    val recipes: List<RecipeListItemResponse>
)
