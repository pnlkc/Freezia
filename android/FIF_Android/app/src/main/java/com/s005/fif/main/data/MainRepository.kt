package com.s005.fif.main.data

import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.main.network.MainService
import com.s005.fif.recipe.dto.RecipeListItemResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import retrofit2.Response
import javax.inject.Inject

interface MainRepository {
    suspend fun getRecommendRecipeList() : Response<RecipeListResponse>
}

class DefaultMainRepository @Inject constructor(
    private val mainService: MainService
) : MainRepository {
    override suspend fun getRecommendRecipeList(): Response<RecipeListResponse> {
        return mainService.getRecommendRecipeList()
    }

}