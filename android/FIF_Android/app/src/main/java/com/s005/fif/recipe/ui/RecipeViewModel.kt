package com.s005.fif.recipe.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.data.LikeFoodListData
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.recipe.data.RecipeRepository
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.dto.toRecipeListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    val recipeListItem = mutableStateListOf<RecipeListItem>()
    var recipeTypeList by mutableStateOf(
        LikeFoodListData.checkList
    )

    init {
        viewModelScope.launch {
            getRecipeList()
        }
    }

    suspend fun getRecipeList() {
        val responseResult = recipeRepository.getRecipeList()

        if (responseResult.isSuccessful) {
            recipeListItem.addAll(responseResult.body()!!.recipes.map { it.toRecipeListItem() })

            Log.d("로그", "RecipeViewModel - getRecipeList() 호출됨 / 응답 성공 : ${recipeListItem.toList()}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - getRecipeList() 호출됨 / 응답 실패 : ${body}")
        }
    }

    fun checkRecipeType(name: String, isChecked: Boolean) {
        val tempList = recipeTypeList.toList()

        recipeTypeList = listOf()

        tempList.forEach {
            if (it.name == name) it.isChecked = isChecked
        }

        recipeTypeList = tempList

        Log.d("로그", "RecipeViewModel - checkRecipeType() 호출됨 / ${recipeTypeList}")
    }
}