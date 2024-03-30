package com.s005.fif.chat.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResultResponse(
    val reply: String,
    val recommendList: List<String>,
    val recipeList: List<ChatResultRecipeListItemResponse>
)

data class ChatResult(
    val reply: String,
    val recommendList: List<String>,
    val recipeList: List<ChatResultRecipeListItem>
)

fun ChatResultResponse.toChatResult() = ChatResult(
    reply, recommendList, recipeList.map { it.toChatResultRecipeListItem() }
)