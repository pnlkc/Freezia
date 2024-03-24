package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientItemResponse(
    val ingredientId: Int,
    val name: String,
    val image: String,
    val amounts: Int,
    val unit: String
)
