package com.s005.fif.recipe.data

import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import com.s005.fif.recipe.network.RecipeService
import retrofit2.Response
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getRecipeList(): Response<RecipeListResponse>
}

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun getRecipeList(): Response<RecipeListResponse> {
        return recipeService.getRecipeList()
    }
}