package com.s005.fif.shopping_list.network

import com.s005.fif.shopping_list.dto.ShoppingListCheckRequest
import com.s005.fif.shopping_list.dto.ShoppingListCheckResponse
import com.s005.fif.shopping_list.dto.ShoppingListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ShoppingListService {
    @GET("shopping-list")
    suspend fun getShoppingList(): Response<ShoppingListResponse>

    @PATCH("shopping-list/{shoppingListId}")
    suspend fun checkShoppingListItem(
        @Path("shoppingListId") shoppingListId: Int,
        @Body shoppingListCheckRequest: ShoppingListCheckRequest
    ): Response<ShoppingListCheckResponse>
}