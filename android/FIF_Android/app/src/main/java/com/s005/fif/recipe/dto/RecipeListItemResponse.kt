package com.s005.fif.recipe.dto

import com.s005.fif.main.dto.RecommendIngredientItemResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.toJavaLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class RecipeListItemResponse(
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
    val recommendDesc: String = "",
    val recipeTypes: String,
    val serving: Int,
)

data class RecipeListItem(
    val recipeId: Int,
    val name: String,
    val createDate: java.time.LocalDate,
    val cookTime: Int,
    val calorie: Int,
    val ingredientList: List<IngredientItem>,
    val seasoningList: List<IngredientItem>,
    val imgUrl: String,
    val saveYn: Boolean,
    val completeYn: Boolean,
    val recommendType: Int,
    val recommendDesc: String,
    val recipeTypes: String,
    val serving: Int,
)

fun RecipeListItemResponse.toRecipeListItem() = RecipeListItem(
    recipeId,
    name,
    createDate.toJavaLocalDate(),
    cookTime,
    calorie,
    ingredientList.map { it.toIngredientItem() },
    seasoningList.map { it.toIngredientItem() },
    imgUrl,
    saveYn,
    completeYn,
    recommendType,
    recommendDesc,
    recipeTypes,
    serving
)