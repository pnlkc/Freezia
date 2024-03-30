package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompleteRecipeRecordResponse(
    val result: String,
    val completeCooks: List<CompleteRecipeRecordItemResponse>
)