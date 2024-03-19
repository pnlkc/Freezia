package com.s005.fif.ui.navigation

sealed class NavigationDestination(val route: String) {
    data object Main : NavigationDestination("main")

    data object UserSelect : NavigationDestination("user_select")

    data object UserOnboarding : NavigationDestination("user_onboarding")

    data object UserProfile : NavigationDestination("user_profile")

    data object RecipePreferenceSetting : NavigationDestination("recipe_preference_setting")

    data object RecipeHistory : NavigationDestination("recipe_history")

    data object ShoppingList : NavigationDestination("shopping_list")

    data object RecipeList : NavigationDestination("recipe_list")
}