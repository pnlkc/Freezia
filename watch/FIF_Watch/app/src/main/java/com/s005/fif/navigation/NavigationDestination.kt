package com.s005.fif.navigation

sealed class NavigationDestination(val route: String) {
    data object Main : NavigationDestination("main")

    data object ShoppingList : NavigationDestination("shopping_list")
}