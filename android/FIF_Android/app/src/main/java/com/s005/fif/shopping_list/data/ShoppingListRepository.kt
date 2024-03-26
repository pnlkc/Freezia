package com.s005.fif.shopping_list.data

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.shopping_list.dto.ShoppingListCheckRequest
import com.s005.fif.shopping_list.dto.ShoppingListCheckResponse
import com.s005.fif.shopping_list.dto.ShoppingListResponse
import com.s005.fif.shopping_list.network.ShoppingListService
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

interface ShoppingListRepository {
    suspend fun getShoppingList(): Response<ShoppingListResponse>

    suspend fun checkShoppingListItem(
        shoppingListId: Int,
        shoppingListCheckRequest: ShoppingListCheckRequest
    ): Response<ShoppingListCheckResponse>

    suspend fun deleteShoppingListItem(
        shoppingListId: Int
    ): Response<DefaultResponse>
}

class DefaultShoppingListRepository @Inject constructor(
    private val shoppingListService: ShoppingListService
) : ShoppingListRepository {
    override suspend fun getShoppingList(): Response<ShoppingListResponse> {
        return shoppingListService.getShoppingList()
    }

    override suspend fun checkShoppingListItem(
        shoppingListId: Int,
        shoppingListCheckRequest: ShoppingListCheckRequest,
    ): Response<ShoppingListCheckResponse> {
        return shoppingListService.checkShoppingListItem(shoppingListId, shoppingListCheckRequest)
    }

    override suspend fun deleteShoppingListItem(shoppingListId: Int): Response<DefaultResponse> {
        return shoppingListService.deleteShoppingListItem(shoppingListId)
    }
}
