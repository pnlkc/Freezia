package com.s005.fif.recipe.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.recipe.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    suspend fun moveRecipeStep(step: Int) {
        val responseResult = recipeRepository.moveRecipeStep(step)

        if (responseResult.isSuccessful) {
            Log.d("로그", "RecipeViewModel - moveRecipeStep() 호출됨 / 응답 성공 : ${responseResult.body()!!}")
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)

            Log.d("로그", "RecipeViewModel - moveRecipeStep() 호출됨 / 응답 실패 : ${body}")
        }
    }
}