package com.s005.fif.ui.navigation

sealed class NavigationDestination(val route: String) {
    data object Splash : NavigationDestination("splash")

    data object Main : NavigationDestination("main")

    data object UserSelect : NavigationDestination("user_select")

    data object UserOnboarding : NavigationDestination("user_onboarding") {
        const val MODE = "mode"
    }

    data object UserProfile : NavigationDestination("user_profile")

    data object RecipePreferenceSetting : NavigationDestination("recipe_preference_setting")

    data object RecipeHistory : NavigationDestination("recipe_history") {
        const val MODE = "mode"
    }

    data object ShoppingList : NavigationDestination("shopping_list")

    data object ShoppingListAdd : NavigationDestination("shopping_list_add")

    data object RecipeList : NavigationDestination("recipe_list")

    data object Chat : NavigationDestination("chat")

    data object ChatRecipeDetail : NavigationDestination("chat_recipe_detail") {
        const val RECIPE_IDX = "recipe_idx"
    }

    data object ChatRecipeStep : NavigationDestination("chat_recipe_step") {
        const val RECIPE_IDX = "recipe_idx"
    }

    data object RecipeDetail : NavigationDestination("recipe_detail") {
        const val RECIPE_ID = "recipe_id"
    }

    data object RecipeStep : NavigationDestination("recipe_step") {
        const val RECIPE_ID = "recipe_id"
    }

    data object RecipeComplete : NavigationDestination("recipe_complete") {
        const val RECIPE_ID = "recipe_id"
    }

    data object IngredientAdd : NavigationDestination("ingredient_add")

    data object IngredientRemove : NavigationDestination("ingredient_remove") {
        const val RECIPE_ID = "recipe_id"
    }
}