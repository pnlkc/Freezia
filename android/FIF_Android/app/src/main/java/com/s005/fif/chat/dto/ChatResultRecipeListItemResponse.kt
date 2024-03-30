package com.s005.fif.chat.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResultRecipeListItemResponse(
    val name: String,
    val ingredientList: List<ChatResultIngredientResponse>,
    val seasoningList: List<ChatResultIngredientResponse>,
    val cookTime: String,
    val calorie: String? = null,
    val carlorie: String? = null,
    val servings: String,
    val recipeType: String,
    val recipeSteps: List<ChatResultRecipeStepResponse>
)

data class ChatResultRecipeListItem(
    val name: String,
    val ingredientList: List<ChatResultIngredient>,
    val seasoningList: List<ChatResultIngredient>,
    val cookTime: String,
    val calorie: String?,
    val carlorie: String?,
    val servings: String,
    val recipeType: String,
    val recipeSteps: List<ChatResultRecipeStep>
)

fun ChatResultRecipeListItemResponse.toChatResultRecipeListItem() = ChatResultRecipeListItem(
    name,
    ingredientList.map { it.toChatResultIngredient() },
    seasoningList.map { it.toChatResultIngredient() },
    cookTime,
    calorie,
    carlorie,
    servings,
    recipeType,
    recipeSteps.map { it.toChatResultRecipeStep() }
)