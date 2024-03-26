package com.s005.fif.recipe.data

import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.recipe.dto.CompleteRecipeRecordResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import com.s005.fif.recipe.network.RecipeService
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getRecipeList(): Response<RecipeListResponse>

    suspend fun getCompleteRecipeRecord(
        recipeId: Int
    ): Response<CompleteRecipeRecordResponse>
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
}