package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeHistoryListResponse(
    val result: String,
    val recipes: List<RecipeItemResponse>
)