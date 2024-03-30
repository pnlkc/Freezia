package com.s005.fif.shopping_list.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListCheckResponse(
    val result: String,
    val checkYn: Boolean
)
