package com.s005.fif.main.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.main.data.MainRepository
import com.s005.fif.main.dto.RecipeList
import com.s005.fif.main.dto.toRecipeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var recommendRecipeList by mutableStateOf(listOf<RecipeList>())

    init {
        viewModelScope.launch {
            getRecommendRecipeList()
        }
    }

    private suspend fun getRecommendRecipeList() {
        val responseResult = mainRepository.getRecommendRecipeList()

        if (responseResult.isSuccessful) {
            recommendRecipeList = responseResult.body()!!.recipes.map { it.toRecipeList() }
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "MainViewModel - getRecommendRecipeList() 호출됨 / 응답 실패 : ${body}")
        }
    }
}