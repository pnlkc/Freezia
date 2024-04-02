package com.s005.fif.main.network

import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.recipe.dto.RecipeListItemResponse
import com.s005.fif.recipe.dto.RecipeListResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {
    @GET("recipes/recommendation")
    suspend fun getRecommendRecipeList() : Response<RecipeListResponse>
}