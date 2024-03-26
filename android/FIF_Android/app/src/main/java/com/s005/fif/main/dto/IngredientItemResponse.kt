package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientItemResponse(
    val ingredientId: Int,
    val name: String,
    val imgUrl: String,
    val unit: String,
    val amounts: Double
)
