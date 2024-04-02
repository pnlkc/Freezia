package com.s005.fif.recipe.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.fcm.RecipeLiveData
import com.s005.fif.recipe.data.RecipeRepository
import com.s005.fif.recipe.dto.MoveRecipeStepRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    suspend fun moveRecipeStep(step: Int) {
        RecipeLiveData.recipeStep.postValue(step - 1)

        Log.d("로그", "RecipeViewModel - moveRecipeStep() 호출됨")

        val responseResult = recipeRepository.moveRecipeStep(step, MoveRecipeStepRequest(2))

        if (responseResult.isSuccessful) {
            Log.d("로그", "RecipeViewModel - moveRecipeStep() 호출됨 / 응답 성공 : ${responseResult.body()!!}")
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)

            Log.d("로그", "RecipeViewModel - moveRecipeStep() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun disconnectRecipe() {
        val responseResult = recipeRepository.disconnectRecipe(RecipeLiveData.recipeData!!.recipeInfo.recipeId)

        if (responseResult.isSuccessful) {
            Log.d("로그", "RecipeViewModel - disconnectRecipe() 호출됨 / 응답 성공 : ${responseResult.body()!!}")
        } else {
//            val body =
//                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
//
//            Log.d("로그", "RecipeViewModel - disconnectRecipe() 호출됨 / 응답 실패 : ${body}")
        }
    }
}