package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompleteIngredientRequest(
    val name: String
)