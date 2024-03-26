package com.s005.fif.main.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.toJavaLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class RecipeListResponse(
    val recipeId: Int,
    val name: String,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val createDate: LocalDate,
    val cookTime: Int,
    val calorie: Int,
    val ingredientList: List<IngredientItemResponse>,
    val seasoningList: List<IngredientItemResponse>,
    val imgUrl: String,
    val saveYn: Boolean,
    val completeYn: Boolean,
    val recommendType: Int,
    val recommendDesc: String,
    val recipeTypes: String,
    val servings: Int
)

data class RecipeList(
    val recipeId: Int,
    val name: String,
    val createDate: java.time.LocalDate,
    val cookTime: Int,
    val calorie: Int,
    val ingredientList: List<IngredientItemResponse>,
    val seasoningList: List<IngredientItemResponse>,
    val imgUrl: String,
    val saveYn: Boolean,
    val completeYn: Boolean,
    val recommendType: Int,
    val recommendDesc: String,
    val recipeTypes: String,
    val servings: Int
)

fun RecipeListResponse.toRecipeList() = RecipeList(
    recipeId, name, createDate.toJavaLocalDate(), cookTime, calorie, ingredientList, seasoningList, imgUrl, saveYn, completeYn, recommendType, recommendDesc, recipeTypes, servings
)