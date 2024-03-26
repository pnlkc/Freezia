package com.s005.fif.user.ui.recipe_history.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.user.data.UserRepository
import com.s005.fif.user.dto.RecipeItem
import com.s005.fif.user.dto.toRecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class RecipeHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var savedRecipeList by mutableStateOf(listOf<RecipeItem>())
    var completedRecipeList by mutableStateOf(listOf<RecipeItem>())

    init {
        viewModelScope.launch {
            getSavedRecipeList()
            getCompletedRecipeList()
        }
    }

    suspend fun getSavedRecipeList() {
        val responseResult = userRepository.getSavedRecipeList()

        if (responseResult.isSuccessful) {
            savedRecipeList = responseResult.body()!!.recipes.map { it.toRecipeItem() }

            Log.d("로그", "RecipeHistoryViewModel - getSavedRecipeList() 호출됨 / 응답 성공 : ${savedRecipeList}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeHistoryViewModel - getSavedRecipeList() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun getCompletedRecipeList() {
        val responseResult = userRepository.getCompletedRecipeList()

        if (responseResult.isSuccessful) {
            completedRecipeList = responseResult.body()!!.recipes.map { it.toRecipeItem() }

            Log.d("로그", "RecipeHistoryViewModel - getCompletedRecipeList() 호출됨 / 응답 성공 : ${completedRecipeList}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeHistoryViewModel - getCompletedRecipeList() 호출됨 / 응답 실패 : ${body}")
        }
    }
}