package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecommendRecipeListResponse(
    val result: String,
    val recipes: List<RecommendRecipeListItemResponse>
)