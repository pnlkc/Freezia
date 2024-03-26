package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeHistoryItemResponse(
    val recipeId: Int,
    val name: String,
    val cookTime: Int,
    val imgUrl: String,
    val saveYn: Boolean
)

data class RecipeHistoryItem(
    val recipeId: Int,
    val name: String,
    val cookTime: Int,
    val imgUrl: String,
    val saveYn: Boolean
)

fun RecipeHistoryItemResponse.toRecipeHistoryItem() = RecipeHistoryItem(
    recipeId, name, cookTime, imgUrl, saveYn
)