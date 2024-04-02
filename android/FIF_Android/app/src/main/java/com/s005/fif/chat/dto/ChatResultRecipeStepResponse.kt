package com.s005.fif.chat.dto

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class ChatResultRecipeStepResponse(
    val type: String,
    val name: String,
    val description: String,
    val duration: Int,
    val tip: String
)

data class ChatResultRecipeStep(
    val type: String,
    val name: String,
    val description: String,
    val duration: Int,
    val tip: String
)

fun ChatResultRecipeStepResponse.toChatResultRecipeStep() = ChatResultRecipeStep(
    type, name, description, duration, tip
)