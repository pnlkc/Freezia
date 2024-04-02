package com.s005.fif.fridge_ingredient.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.fridge_ingredient.data.FridgeIngredientRepository
import com.s005.fif.fridge_ingredient.dto.FridgeIngredientItem
import com.s005.fif.fridge_ingredient.dto.toFridgeIngredientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class FridgeIngredientViewModel @Inject constructor(
    private val fridgeIngredientRepository: FridgeIngredientRepository
) : ViewModel() {
    val fridgeIngredientList = mutableStateListOf<FridgeIngredientItem>()

    suspend fun getFridgeIngredientList() {
        val responseResult = fridgeIngredientRepository.getFridgeIngredientList()

        if (responseResult.isSuccessful) {
            fridgeIngredientList.clear()
            fridgeIngredientList.addAll(responseResult.body()!!.fridgeIngredients.map { it.toFridgeIngredientItem() })

            Log.d("로그", "FridgeIngredientViewModel - getFridgeIngredientList() 호출됨 / 응답 성공 : ${fridgeIngredientList}")
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)

            Log.d("로그", "FridgeIngredientViewModel - getFridgeIngredientList() 호출됨 / 응답 실패 : ${body}")
        }
    }

}