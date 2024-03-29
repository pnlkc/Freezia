package com.s005.fif.fridge_ingredient.network

import com.s005.fif.fridge_ingredient.dto.FridgeIngredientListResponse
import retrofit2.Response
import retrofit2.http.GET

interface FridgeIngredientService {
    @GET("fridge-ingredients")
    suspend fun getFridgeIngredientList(): Response<FridgeIngredientListResponse>
}