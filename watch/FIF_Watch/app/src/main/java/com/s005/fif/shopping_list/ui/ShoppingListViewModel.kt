package com.s005.fif.shopping_list.ui

import androidx.lifecycle.ViewModel
import com.s005.fif.shopping_list.data.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

}