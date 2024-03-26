package com.s005.fif.ui.navigation

sealed class NavigationDestination(val route: String) {
    data object Splash : NavigationDestination("splash")

    data object Main : NavigationDestination("main")

    data object UserSelect : NavigationDestination("user_select")

    data object UserOnboarding : NavigationDestination("user_onboarding")

    data object UserProfile : NavigationDestination("user_profile")

    data object RecipePreferenceSetting : NavigationDestination("recipe_preference_setting")

    data object RecipeHistory : NavigationDestination("recipe_history") {
        const val MODE = "mode"
    }

    data object ShoppingList : NavigationDestination("shopping_list")

    data object RecipeList : NavigationDestination("recipe_list")

    data object RecipeChat : NavigationDestination("recipe_chat")

    data object RecipeDetail : NavigationDestination("recipe_detail") {
        const val RECIPE_ID = "recipe_id"
    }

    data object RecipeStep : NavigationDestination("recipe_step")

    data object RecipeComplete : NavigationDestination("recipe_complete")

    data object IngredientAdd : NavigationDestination("ingredient_add")

    data object IngredientRemove : NavigationDestination("ingredient_remove")
}