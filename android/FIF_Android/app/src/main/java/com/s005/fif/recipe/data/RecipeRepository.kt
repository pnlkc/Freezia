package com.s005.fif.recipe.data

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.recipe.dto.AddRecipeHistoryRequest
import com.s005.fif.recipe.dto.CompleteRecipeRecordResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import com.s005.fif.recipe.dto.RecipeStepListResponse
import com.s005.fif.recipe.network.RecipeService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getRecipeList(): Response<RecipeListResponse>

    suspend fun getCompleteRecipeRecord(
        recipeId: Int
    ): Response<CompleteRecipeRecordResponse>

    suspend fun getRecipeStepList(
        recipeId: Int
    ): Response<RecipeStepListResponse>

    suspend fun saveRecipe(
        recipeId: Int
    ): Response<DefaultResponse>

    suspend fun addRecipeHistory(
        recipeId: Int,
        addRecipeHistoryRequest: AddRecipeHistoryRequest
    ): Response<DefaultResponse>

    suspend fun deleteRecipeHistory(
        completeCookId: Int,
    ): Response<DefaultResponse>
}

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun getRecipeList(): Response<RecipeListResponse> {
        return recipeService.getRecipeList()
    }

    override suspend fun getCompleteRecipeRecord(recipeId: Int): Response<CompleteRecipeRecordResponse> {
        return recipeService.getCompleteRecipeRecord(recipeId)
    }

    override suspend fun getRecipeStepList(recipeId: Int): Response<RecipeStepListResponse> {
        return recipeService.getRecipeStepList(recipeId)
    }

    override suspend fun saveRecipe(recipeId: Int): Response<DefaultResponse> {
        return recipeService.saveRecipe(recipeId)
    }

    override suspend fun addRecipeHistory(
        recipeId: Int,
        addRecipeHistoryRequest: AddRecipeHistoryRequest,
    ): Response<DefaultResponse> {
        return recipeService.addRecipeHistory(recipeId, addRecipeHistoryRequest)
    }

    override suspend fun deleteRecipeHistory(completeCookId: Int): Response<DefaultResponse> {
        return recipeService.deleteRecipeHistory(completeCookId)
    }
}