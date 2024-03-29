package com.s005.fif.fridge_ingredient.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.toJavaLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class FridgeIngredientItemResponse(
    val fridgeIngredientId: Int,
    val name: String,
    val imgUrl: String,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val insertionDate: LocalDate,
    @Serializable(with = LocalDateIso8601Serializer::class)
    val expirationDate: LocalDate
)

data class FridgeIngredientItem(
    val fridgeIngredientId: Int,
    val name: String,
    val imgUrl: String,
    val insertionDate: java.time.LocalDate,
    val expirationDate: java.time.LocalDate
)

fun FridgeIngredientItemResponse.toFridgeIngredientItem() = FridgeIngredientItem(
    fridgeIngredientId, name, imgUrl, insertionDate.toJavaLocalDate(), expirationDate.toJavaLocalDate()
)