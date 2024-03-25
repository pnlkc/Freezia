package com.s005.fif.shopping_list.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListItemResponse(
    val shoppingListId: Int,
    val name: String,
    val checkYn: Boolean
)

data class ShoppingListItem(
    val shoppingListId: Int,
    val name: String,
    var checkYn: Boolean
)

fun ShoppingListItemResponse.toShoppingList() = ShoppingListItem(
    shoppingListId, name, checkYn
)