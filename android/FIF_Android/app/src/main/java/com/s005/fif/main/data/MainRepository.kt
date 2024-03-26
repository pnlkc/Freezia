package com.s005.fif.main.data

import com.s005.fif.main.dto.RecommendRecipeListResponse
import com.s005.fif.main.network.MainService
import retrofit2.Response
import javax.inject.Inject

interface MainRepository {
    suspend fun getRecommendRecipeList() : Response<RecommendRecipeListResponse>
}

class DefaultMainRepository @Inject constructor(
    private val mainService: MainService
) : MainRepository {
    override suspend fun getRecommendRecipeList(): Response<RecommendRecipeListResponse> {
        return mainService.getRecommendRecipeList()
    }

}