package com.s005.fif.recipe.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.common.data.LikeFoodListData
import com.s005.fif.common.data.toIngredientItem
import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.recipe.data.RecipeRepository
import com.s005.fif.recipe.dto.AddRecipeHistoryRequest
import com.s005.fif.recipe.dto.CompleteRecipeRecordItem
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.recipe.dto.RecipeListItem
import com.s005.fif.recipe.dto.RecipeStepItem
import com.s005.fif.recipe.dto.toAddRecipeHistoryRequest
import com.s005.fif.recipe.dto.toCompleteRecipeRecordItem
import com.s005.fif.recipe.dto.toRecipeListItem
import com.s005.fif.recipe.dto.toRecipeStepItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    val recipeList = mutableStateListOf<RecipeListItem>()
    var recipeTypeList by mutableStateOf(
        LikeFoodListData.checkList
    )
    var servings by mutableIntStateOf(1)
    val completeRecipeRecordList = mutableStateListOf<CompleteRecipeRecordItem>()
    val recipeStepList = mutableStateListOf<RecipeStepItem>()
    val addIngredientList = mutableStateListOf<IngredientItem>()
    val addTempIngredientList = mutableStateListOf<IngredientItemData>()
    var inputText by mutableStateOf("")
    val removeIngredientList = mutableStateListOf<IngredientItem>()

    init {
        viewModelScope.launch {
            getRecipeList()
        }
    }

    suspend fun getRecipeList() {
        val responseResult = recipeRepository.getRecipeList()

        if (responseResult.isSuccessful) {
            recipeList.clear()
            recipeList.addAll(responseResult.body()!!.recipes.map { it.toRecipeListItem() })

            Log.d("로그", "RecipeViewModel - getRecipeList() 호출됨 / 응답 성공 : ${recipeList.toList()}")
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
            completeRecipeRecordList.addAll(responseResult.body()!!.completeCooks.map { it.toCompleteRecipeRecordItem() }.reversed())

            Log.d(
                "로그",
                "RecipeViewModel - getCompleteRecipeRecord() 호출됨 / 응답 성공 : ${completeRecipeRecordList.toList()}"
            )
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - getCompleteRecipeRecord() 호출됨 / 응답 실패 : ${body}")
        }
    }

    fun getRecipe(recipeId: Int): RecipeListItem? {
        return if (recipeList.isNotEmpty()) recipeList.first { it.recipeId == recipeId } else null
    }

    suspend fun getRecipeStep(recipeId: Int) {
        val responseResult = recipeRepository.getRecipeStepList(recipeId = recipeId)

        if (responseResult.isSuccessful) {
            val stepList = responseResult.body()!!.recipeSteps.map { it.toRecipeStepItem() }

            recipeStepList.clear()
            recipeStepList.addAll(stepList)

            Log.d(
                "로그",
                "RecipeViewModel - getRecipeStep() 호출됨 / 응답 성공 : ${recipeStepList.toList()}"
            )
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - getRecipeStep() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun saveRecipe(recipeId: Int) {
        val responseResult = recipeRepository.saveRecipe(recipeId = recipeId)

        if (responseResult.isSuccessful) {
            val body = responseResult.body()!!

            val idx = recipeList.indexOfFirst { it.recipeId == recipeId }
            recipeList[idx] = recipeList[idx].copy(saveYn = !recipeList[idx].saveYn)

            Log.d("로그", "RecipeViewModel - saveRecipe() 호출됨 / 응답 성공 : ${body}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - saveRecipe() 호출됨 / 응답 실패 : ${body}")
        }
    }

    fun removeAddIngredient(id: Int) {
        val idx = addIngredientList.indexOfFirst { it.ingredientId == id }
        addIngredientList.removeAt(idx)
    }

    fun addAddIngredient() {
        addTempIngredientList.map { it.toIngredientItem() }.forEach {
            if (!addIngredientList.contains(it)) addIngredientList.add(it)
        }

        addTempIngredientList.clear()
    }

    fun removeTempAddIngredient(item: IngredientItemData) {
        val idx = addTempIngredientList.indexOfFirst { it.ingredientId == item.ingredientId }
        if (idx != -1) addTempIngredientList.removeAt(idx)
    }

    fun addTempAddIngredient(item: IngredientItemData) {
        if (!addTempIngredientList.contains(item)) {
            addTempIngredientList.add(item)
        }
    }

    fun initRemoveIngredientList(recipeId: Int) {
        removeIngredientList.clear()

        val recipe = getRecipe(recipeId)!!

        removeIngredientList.addAll(recipe.ingredientList)
        removeIngredientList.addAll(recipe.seasoningList)
    }

    fun checkRemoveIngredient(id: Int, isChecked: Boolean) {
        val tempList = removeIngredientList.toList()
        removeIngredientList.clear()

        tempList.forEach {
            if (it.ingredientId == id) {
                it.isChecked = isChecked
            }
        }

        removeIngredientList.addAll(tempList)
    }

    fun clearIngredientList() {
        addIngredientList.clear()
        addTempIngredientList.clear()
        removeIngredientList.clear()
        inputText = ""
    }

    suspend fun addRecipeHistory(recipeId: Int) {
        val responseResult = recipeRepository.addRecipeHistory(
            recipeId,
            AddRecipeHistoryRequest(
                addIngredients = addIngredientList.map { it.toAddRecipeHistoryRequest() },
                removeIngredients = removeIngredientList.filter { it.isChecked }.map { it.toAddRecipeHistoryRequest() },
            )
        )

        if (responseResult.isSuccessful) {
            val body = responseResult.body()!!

            Log.d("로그", "RecipeViewModel - addRecipeHistory() 호출됨 / 응답 성공 : ${body}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - addRecipeHistory() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun deleteRecipeHistory(completeCookId: Int, recipeId: Int) {
        val responseResult = recipeRepository.deleteRecipeHistory(completeCookId)

        if (responseResult.isSuccessful) {
            val body = responseResult.body()!!

            getCompleteRecipeRecord(recipeId)

            Log.d("로그", "RecipeViewModel - deleteRecipeHistory() 호출됨 / 응답 성공 : ${body}")
        } else {
            val body = Json.decodeFromString<ErrorResponse>(
                responseResult.errorBody()?.string()!!
            )

            Log.d("로그", "RecipeViewModel - deleteRecipeHistory() 호출됨 / 응답 실패 : ${body}")
        }
    }
}