package com.s005.fif.recipe.network

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.recipe.dto.RecipeCompleteRequest
import com.s005.fif.recipe.dto.RecipeDetailResponse
import com.s005.fif.recipe.dto.RecipeStepListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes/{recipeId}")
    suspend fun getRecipeDetail(
        @Path("recipeId") recipeId: Int
    ): Response<RecipeDetailResponse>

    @GET("recipes/{recipeId}/steps")
    suspend fun getRecipeStepList(
        @Path("recipeId") recipeId: Int
    ): Response<RecipeStepListResponse>

    @POST("recipes/{recipeId}/complete")
    suspend fun addRecipeComplete(
        @Path("recipeId") recipeId: Int,
        @Body recipeCompleteRequest: RecipeCompleteRequest
    ): Response<DefaultResponse>

    @GET("recipes/steps/{step}")
    suspend fun moveRecipeStep(
        @Path("step") step: Int
    ) : Response<DefaultResponse>

    @GET("recipes/{recipeId}/galaxy-watch")
    suspend fun disconnectRecipe(
        @Path("recipeId") recipeId: Int
    ): Response<DefaultResponse>
}