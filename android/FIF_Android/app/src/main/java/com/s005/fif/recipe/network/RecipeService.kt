package com.s005.fif.recipe.network

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.recipe.dto.AddRecipeHistoryRequest
import com.s005.fif.recipe.dto.CompleteRecipeRecordResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import com.s005.fif.recipe.dto.RecipeStepListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getRecipeList(): Response<RecipeListResponse>

    @GET("recipes/{recipeId}/complete")
    suspend fun getCompleteRecipeRecord(
        @Path("recipeId") recipeId: Int
    ): Response<CompleteRecipeRecordResponse>

    @GET("recipes/{recipeId}/steps")
    suspend fun getRecipeStepList(
        @Path("recipeId") recipeId: Int
    ): Response<RecipeStepListResponse>

    @PATCH("recipes/{recipeId}/save")
    suspend fun saveRecipe(
        @Path("recipeId") recipeId: Int
    ): Response<DefaultResponse>

    @POST("recipes/{recipeId}/complete")
    suspend fun addRecipeHistory(
        @Path("recipeId") recipeId: Int,
        @Body addRecipeHistoryRequest: AddRecipeHistoryRequest
    ): Response<DefaultResponse>

    @DELETE("recipes/complete/{completeCookId}")
    suspend fun deleteRecipeHistory(
        @Path("completeCookId") completeCookId: Int,
    ): Response<DefaultResponse>
}