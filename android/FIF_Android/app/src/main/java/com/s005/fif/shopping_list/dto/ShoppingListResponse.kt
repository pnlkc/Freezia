package com.s005.fif.shopping_list.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListResponse(
    val result: String,
    val shoppingList: List<ShoppingListItemResponse>
)
