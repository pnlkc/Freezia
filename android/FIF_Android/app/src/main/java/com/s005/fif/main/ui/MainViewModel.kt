package com.s005.fif.main.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.main.data.MainRepository
import com.s005.fif.main.dto.RecommendRecipeListItem
import com.s005.fif.main.dto.toRecommendRecipeListItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.dto.toRecipeListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var recommendRecipeListItem by mutableStateOf(listOf<RecipeListItem>())

    init {
        viewModelScope.launch {
            getRecommendRecipeList()
        }
    }

    suspend fun getRecommendRecipeList() {
        val responseResult = mainRepository.getRecommendRecipeList()

        if (responseResult.isSuccessful) {
            recommendRecipeListItem = responseResult.body()!!.recipes.map { it.toRecipeListItem() }
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "MainViewModel - getRecommendRecipeList() 호출됨 / 응답 실패 : ${body}")
        }
    }
}