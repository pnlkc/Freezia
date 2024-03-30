package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientItemResponse(
    val ingredientId: Int,
    val name: String,
    val image: String,
    val unit: String,
    val amounts: String
)

data class IngredientItem(
    val ingredientId: Int,
    val name: String,
    val image: String,
    val unit: String,
    val amounts: String,
    var isChecked: Boolean = false
)

fun IngredientItemResponse.toIngredientItem() = IngredientItem(
    ingredientId, name, image, unit, amounts
)

fun IngredientItem.toAddRecipeHistoryRequest() = AddRecipeHistoryIngredientRequest(name)