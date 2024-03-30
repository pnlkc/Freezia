package com.s005.fif.chat.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResultIngredientResponse(
    val name: String,
    val amounts: String,
    val unit: String
)

data class ChatResultIngredient(
    val name: String,
    val amounts: String,
    val unit: String
)

fun ChatResultIngredientResponse.toChatResultIngredient() = ChatResultIngredient(
    name, amounts, unit
)