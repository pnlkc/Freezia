package com.s005.fif.recipe.data

import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.recipe.dto.MoveRecipeStepRequest
import com.s005.fif.recipe.dto.RecipeCompleteRequest
import com.s005.fif.recipe.dto.RecipeDetailResponse
import com.s005.fif.recipe.dto.RecipeStepListResponse
import com.s005.fif.recipe.network.RecipeService
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getRecipeDetail(
        recipeId: Int
    ): Response<RecipeDetailResponse>

    suspend fun getRecipeStepList(
        recipeId: Int
    ): Response<RecipeStepListResponse>

    suspend fun addRecipeComplete(
        recipeId: Int,
        recipeCompleteRequest: RecipeCompleteRequest
    ): Response<DefaultResponse>

    suspend fun moveRecipeStep(
        step: Int,
        moveRecipeStepRequest: MoveRecipeStepRequest
    ) : Response<DefaultResponse>

    suspend fun disconnectRecipe(
        recipeId: Int
    ): Response<DefaultResponse>
}

class DefaultRecipeRepository @Inject constructor(
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse> {
        return recipeService.getRecipeDetail(recipeId)
    }

    override suspend fun getRecipeStepList(recipeId: Int): Response<RecipeStepListResponse> {
        return recipeService.getRecipeStepList(recipeId)
    }

    override suspend fun addRecipeComplete(
        recipeId: Int,
        recipeCompleteRequest: RecipeCompleteRequest,
    ): Response<DefaultResponse> {
        return recipeService.addRecipeComplete(recipeId, recipeCompleteRequest)
    }

    override suspend fun moveRecipeStep(step: Int, moveRecipeStepRequest: MoveRecipeStepRequest): Response<DefaultResponse> {
        return recipeService.moveRecipeStep(step, moveRecipeStepRequest)
    }

    override suspend fun disconnectRecipe(recipeId: Int): Response<DefaultResponse> {
        return recipeService.disconnectRecipe(recipeId)
    }
}