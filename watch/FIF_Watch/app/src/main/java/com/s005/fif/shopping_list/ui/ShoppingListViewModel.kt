package com.s005.fif.shopping_list.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.shopping_list.data.ShoppingListRepository
import com.s005.fif.shopping_list.dto.ShoppingListCheckRequest
import com.s005.fif.shopping_list.dto.ShoppingListItem
import com.s005.fif.shopping_list.dto.toShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    val shoppingList = mutableStateOf(listOf<ShoppingListItem>())

    init {
        viewModelScope.launch {
            getShoppingList()
        }
    }

    private suspend fun getShoppingList() {
        val responseResult = shoppingListRepository.getShoppingList()

        if (responseResult.isSuccessful) {
            val tempList = responseResult.body()!!.shoppingList.map { it.toShoppingList() }

            shoppingList.value = tempList.sortedWith(compareBy<ShoppingListItem> { it.checkYn }.thenBy { it.name })
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
            Log.d("로그", "ShoppingListViewModel - getShoppingList() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun checkShoppingListItem(idx: Int, id: Int, isChecked: Boolean) {
        val tempList = shoppingList.value.toMutableList()

        tempList[idx] = tempList[idx].copy(checkYn = isChecked)

        shoppingList.value = tempList.sortedWith(compareBy<ShoppingListItem> { it.checkYn }.thenBy { it.name })

        Log.d("로그", "ShoppingListViewModel - checkShoppingListItem() 호출됨 / ${shoppingList.value}")

        val responseResult = shoppingListRepository.checkShoppingListItem(id, ShoppingListCheckRequest(isChecked))

        if (responseResult.isSuccessful) {
            Log.d("로그", "ShoppingListViewModel - checkShoppingListItem() 호출됨 / 응답 성공 : ${responseResult.body()!!}")
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
            Log.d("로그", "ShoppingListViewModel - checkShoppingListItem() 호출됨 / 응답 실패 : ${body}")
        }
    }
}