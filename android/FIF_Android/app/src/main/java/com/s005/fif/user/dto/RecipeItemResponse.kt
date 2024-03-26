package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeItemResponse(
    val recipeId: Int,
    val name: String,
    val cookTime: Int,
    val imgUrl: String,
    val saveYn: Boolean
)

data class RecipeItem(
    val recipeId: Int,
    val name: String,
    val cookTime: Int,
    val imgUrl: String,
    val saveYn: Boolean
)

fun RecipeItemResponse.toRecipeItem() = RecipeItem(
    recipeId, name, cookTime, imgUrl, saveYn
)