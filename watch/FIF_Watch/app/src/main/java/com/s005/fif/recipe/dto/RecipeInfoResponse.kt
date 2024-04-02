package com.s005.fif.recipe.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class RecipeInfoResponse(
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
    val recipeTypes: String,
    val serving: Int
)
