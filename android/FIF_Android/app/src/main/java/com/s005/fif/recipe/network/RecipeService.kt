package com.s005.fif.recipe.network

import com.s005.fif.recipe.dto.CompleteRecipeRecordResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipeList(): Response<RecipeListResponse>

    @GET("recipes/{recipeId}/complete")
    suspend fun getCompleteRecipeRecord(
        @Path("recipeId") recipeId: Int
    ): Response<CompleteRecipeRecordResponse>
}