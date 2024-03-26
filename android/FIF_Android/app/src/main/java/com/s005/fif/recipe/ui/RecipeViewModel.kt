package com.s005.fif.recipe.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.data.LikeFoodListData
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.recipe.data.RecipeRepository
import com.s005.fif.recipe.dto.CompleteRecipeRecordItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.dto.toCompleteRecipeRecordItem
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
    var servings by mutableIntStateOf(1)
    val completeRecipeRecordList = mutableStateListOf<CompleteRecipeRecordItem>()

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

    fun changeServings(isAdd: Boolean) {
        if (isAdd) {
            servings += 1
        } else {
            if (servings > 1) {
                servings -= 1
            }
        }
    }

    suspend fun getCompleteRecipeRecord(recipeId: Int) {
        val responseResult = recipeRepository.getCompleteRecipeRecord(recipeId)

        if (responseResult.isSuccessful) {
            completeRecipeRecordList.clear()
            completeRecipeRecordList.addAll(responseResult.body()!!.completeCooks.map { it.toCompleteRecipeRecordItem() })

            Log.d("로그", "RecipeViewModel - getCompleteRecipeRecord() 호출됨 / 응답 성공 : ${completeRecipeRecordList.toList()}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - getCompleteRecipeRecord() 호출됨 / 응답 실패 : ${body}")
        }
    }
}