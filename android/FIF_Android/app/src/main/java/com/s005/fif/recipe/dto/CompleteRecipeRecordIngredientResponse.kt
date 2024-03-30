package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompleteRecipeRecordIngredientResponse(
    val ingredientId: Int,
    val name: String,
    val image: String
)

data class CompleteRecipeRecordIngredient(
    val ingredientId: Int,
    val name: String,
    val image: String
)

fun CompleteRecipeRecordIngredientResponse.toCompleteRecipeRecordIngredient() = CompleteRecipeRecordIngredient(
    ingredientId, name, image
)