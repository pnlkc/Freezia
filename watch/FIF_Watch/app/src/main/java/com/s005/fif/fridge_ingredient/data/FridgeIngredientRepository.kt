package com.s005.fif.fridge_ingredient.data

import com.s005.fif.fridge_ingredient.dto.FridgeIngredientListResponse
import com.s005.fif.fridge_ingredient.network.FridgeIngredientService
import retrofit2.Response
import javax.inject.Inject

interface FridgeIngredientRepository {
    suspend fun getFridgeIngredientList(): Response<FridgeIngredientListResponse>
}

class DefaultFridgeIngredientRepository @Inject constructor(
    private val fridgeIngredientService: FridgeIngredientService
) : FridgeIngredientRepository {
    override suspend fun getFridgeIngredientList(): Response<FridgeIngredientListResponse> {
        return fridgeIngredientService.getFridgeIngredientList()
    }

}