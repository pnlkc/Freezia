package com.s005.fif.navigation

sealed class NavigationDestination(val route: String) {
    data object Main : NavigationDestination("main")
    data object ShoppingList : NavigationDestination("shopping_list")
    data object RecipeDetail : NavigationDestination("recipe_detail")
    data object RecipeStep : NavigationDestination("recipe_step") {
        const val STEP = "step"
    }
    data object Warning : NavigationDestination("warning")
    data object TimerList : NavigationDestination("timer_list")
    data object TimerDetail : NavigationDestination("timer_detail") {
        const val TIMER_IDX = "timer_idx"
    }
}