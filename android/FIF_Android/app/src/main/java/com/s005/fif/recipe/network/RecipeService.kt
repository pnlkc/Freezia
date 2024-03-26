package com.s005.fif.recipe.network

import com.s005.fif.recipe.dto.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipeList(): Response<RecipeListResponse>
}