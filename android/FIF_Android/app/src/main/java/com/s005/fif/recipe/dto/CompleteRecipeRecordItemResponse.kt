package com.s005.fif.recipe.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.toJavaLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CompleteRecipeRecordItemResponse(
    val completeCookId: Int,
    val addIngredients: List<CompleteRecipeRecordIngredientResponse>,
    val removeIngredient: List<CompleteRecipeRecordIngredientResponse>,
    val memo: String,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val completeDate: LocalDate,
)

data class CompleteRecipeRecordItem(
    val completeCookId: Int,
    val addIngredients: List<CompleteRecipeRecordIngredient>,
    val removeIngredient: List<CompleteRecipeRecordIngredient>,
    val memo: String,
    val completeDate: java.time.LocalDate,
)

fun CompleteRecipeRecordItemResponse.toCompleteRecipeRecordItem() = CompleteRecipeRecordItem(
    completeCookId,
    addIngredients.map { it.toCompleteRecipeRecordIngredient() },
    removeIngredient.map { it.toCompleteRecipeRecordIngredient() },
    memo,
    completeDate.toJavaLocalDate()
)