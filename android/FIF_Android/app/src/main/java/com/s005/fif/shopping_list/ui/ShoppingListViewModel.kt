package com.s005.fif.shopping_list.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.s005.fif.common.data.IngredientListData
import com.s005.fif.common.data.toIngredientItem
import com.s005.fif.common.dto.DefaultResponse
import com.s005.fif.common.dto.ErrorResponse
import com.s005.fif.recipe.dto.IngredientItem
import com.s005.fif.shopping_list.data.ShoppingListRepository
import com.s005.fif.shopping_list.dto.AddShoppingListRequest
import com.s005.fif.shopping_list.dto.ShoppingListCheckRequest
import com.s005.fif.shopping_list.dto.ShoppingListItem
import com.s005.fif.shopping_list.dto.toShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : ViewModel() {
    var shoppingList = mutableStateListOf<ShoppingListItem>()
    val ingredientCheckList = mutableStateListOf<IngredientItem>()
    var inputText by mutableStateOf("")

    init {
        viewModelScope.launch {
            getShoppingList()
        }
    }

    suspend fun getShoppingList() {
        val responseResult = shoppingListRepository.getShoppingList()

        if (responseResult.isSuccessful) {
            val tempList = responseResult.body()!!.shoppingList.map { it.toShoppingList() }

            shoppingList.clear()
            shoppingList.addAll(tempList.sortedWith(compareBy<ShoppingListItem> { it.checkYn }.thenBy { it.name }))
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
            Log.d("로그", "ShoppingListViewModel - getShoppingList() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun checkShoppingListItem(id: Int, isChecked: Boolean) {
        val tempList = shoppingList.toMutableList()

        tempList.forEach {
            if (it.shoppingListId == id) {
                it.checkYn = isChecked
            }
        }

        shoppingList.clear()
        shoppingList.addAll(tempList.sortedBy { it.name })

        val responseResult =
            shoppingListRepository.checkShoppingListItem(id, ShoppingListCheckRequest(isChecked))

        if (responseResult.isSuccessful) {
            Log.d(
                "로그",
                "ShoppingListViewModel - checkShoppingListItem() 호출됨 / 응답 성공 : ${responseResult.body()!!}"
            )
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
            Log.d("로그", "ShoppingListViewModel - checkShoppingListItem() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun deleteShoppingListItem() {
        shoppingList.forEach {
            if (it.checkYn) {
                val responseResult =
                    shoppingListRepository.deleteShoppingListItem(it.shoppingListId)

                if (responseResult.isSuccessful) {
                    Log.d(
                        "로그",
                        "ShoppingListViewModel - deleteShoppingListItem() 호출됨 / 응답 성공 : ${responseResult.body()!!}"
                    )
                } else {
                    val body =
                        Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
                    Log.d(
                        "로그",
                        "ShoppingListViewModel - deleteShoppingListItem() 호출됨 / 응답 실패 : ${body}"
                    )
                }
            }
        }

        val tempList = shoppingList.filter { !it.checkYn }

        shoppingList.clear()
        shoppingList.addAll(tempList)
    }

    suspend fun deleteShoppingListItem(shoppingListId: Int) {
        val responseResult = shoppingListRepository.deleteShoppingListItem(shoppingListId)

        if (responseResult.isSuccessful) {
            Log.d(
                "로그",
                "ShoppingListViewModel - deleteShoppingListItem() 호출됨 / 응답 성공 : ${responseResult.body()!!}"
            )
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)
            Log.d(
                "로그",
                "ShoppingListViewModel - deleteShoppingListItem() 호출됨 / 응답 실패 : ${body}"
            )
        }
    }

    fun initIngredientCheckList() {
        inputText = ""

        ingredientCheckList.clear()
        ingredientCheckList.addAll(IngredientListData.list.map { it.toIngredientItem() })

        ingredientCheckList.forEach { item ->
            if (shoppingList.toList().count { it.name == item.name } > 0) {
                item.isChecked = true
            }
        }
    }

    fun checkIngredientCheckList(id: Int, isChecked: Boolean) {
        val idx = ingredientCheckList.indexOfFirst { it.ingredientId == id }

        if (idx != -1) {
            ingredientCheckList[idx] = ingredientCheckList[idx].copy(isChecked = isChecked)
        }
    }

    suspend fun addShoppingListItem(ingredientItem: IngredientItem) {
        val responseResult =
            shoppingListRepository.addShoppingListItem(AddShoppingListRequest(ingredientItem.name))

        if (responseResult.isSuccessful) {
            Log.d(
                "로그",
                "ShoppingListViewModel - addShoppingListItem() 호출됨 / 응답 성공 : ${responseResult.body()!!}"
            )
        } else {
            val body =
                Json.decodeFromString<ErrorResponse>(responseResult.errorBody()?.string()!!)

            Log.d("로그", "ShoppingListViewModel - addShoppingListItem() 호출됨 / 응답 실패 : ${body}")
        }
    }

    suspend fun addIngredientShoppingList() {
        viewModelScope.launch {
            ingredientCheckList.forEach { item ->
                val cnt = shoppingList.count { it.name == item.name }

                if (cnt > 0) {
                    shoppingList.forEach {
                        if (it.name == item.name && !item.isChecked) {
                            deleteShoppingListItem(it.shoppingListId)
                        }
                    }
                } else {
                    if (item.isChecked) {
                        addShoppingListItem(item)
                    }
                }
            }

            getShoppingList()
        }
    }
}