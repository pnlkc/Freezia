package com.s005.fif.fcm

import kotlinx.serialization.Serializable

@Serializable
data class RecipeData(
    val recipeInfo: RecipeInfo,
    val recipeSteps: List<RecipeStep>
)

@Serializable
data class RecipeInfo(
    val recipeId: Int,
    val name: String,
    val image: String,
    val cookTime: Int,
    val calorie: Int,
    val servings: Int,
    val ingredientList: List<Ingredient>,
    val seasoningList: List<Ingredient>,
    val isSaved: Boolean,
    val recipeType: Int,
    val recipeSteps: String? = null
)

@Serializable
data class RecipeStep(
    val recipeStepId: Int,
    val stepNumber: Int,
    val description: String,
    val descriptionWatch: String,
    val type: Int,
    val tip: String,
    val timer: Int
)

@Serializable
data class Ingredient(
    val name: String,
    val image: String,
    val amount: Int,
    val unit: String
)