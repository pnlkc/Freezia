package com.s005.fif.chat.data

import com.s005.fif.chat.dto.ChatResultRecipeListItem

data class ChatListItem(
    val chatType: ChatType,
    val content: String,
    val recipeList: List<ChatResultRecipeListItem> = listOf(),
    val recommendPrompt: List<String> = listOf()
)
