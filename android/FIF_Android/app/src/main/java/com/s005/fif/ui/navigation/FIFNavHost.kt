package com.s005.fif.ui.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.main.ui.MainViewModel
import com.s005.fif.main.ui.SplashScreen
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.chat.RecipeChatScreen
import com.s005.fif.recipe.ui.complete.IngredientAddScreen
import com.s005.fif.recipe.ui.complete.IngredientRemoveScreen
import com.s005.fif.recipe.ui.complete.RecipeCompleteScreen
import com.s005.fif.recipe.ui.detail.RecipeDetailScreen
import com.s005.fif.recipe.ui.list.RecipeListScreen
import com.s005.fif.recipe.ui.step.RecipeStepScreen
import com.s005.fif.shopping_list.ui.ShoppingListAddScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen
import com.s005.fif.shopping_list.ui.ShoppingListViewModel
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.onboarding.UserOnboardingMode
import com.s005.fif.user.ui.onboarding.UserOnboardingScreen
import com.s005.fif.user.ui.profile.RecipePreferenceSettingScreen
import com.s005.fif.user.ui.profile.UserProfileScreen
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryScreen
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryViewModel
import com.s005.fif.user.ui.select.UserSelectScreen

@Composable
fun FIFNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val modifierN = modifier
        .navigationBarsPadding()
    val modifierSN = modifier
        .statusBarsPadding()
        .navigationBarsPadding()
    val modifierSNI = modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding()

    val userViewModel: UserViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val recipeHistoryViewModel: RecipeHistoryViewModel = hiltViewModel()
    val shoppingListViewModel: ShoppingListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Splash.route,
        modifier = modifier,
    ) {
        composable(route = NavigationDestination.Splash.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            SplashScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                navigateToMain = {
                    navController.navigate(NavigationDestination.Main.route) {
                        launchSingleTop = true
                    }
                },
                navigateToUserSelect = {
                    navController.navigate(NavigationDestination.UserSelect.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.Main.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            MainScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                mainViewModel = mainViewModel,
                navigateToUserProfile = {
                    navController.navigate(NavigationDestination.UserProfile.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeList = {
                    navController.navigate(NavigationDestination.RecipeList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeChat = {
                    navController.navigate(NavigationDestination.RecipeChat.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeDetail = { recipeInt ->
                    navController.navigate("${NavigationDestination.RecipeDetail.route}/${recipeInt}") {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserSelect.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserSelectScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                navigateToUserOnboarding = {
                    navController.navigate("${NavigationDestination.UserOnboarding.route}/0") {
                        launchSingleTop = true
                    }
                },
                navigateToMain = {
                    navController.navigate(NavigationDestination.Main.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${NavigationDestination.UserOnboarding.route}/{${NavigationDestination.UserOnboarding.MODE}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeHistory.MODE) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val mode =
                navBackStackEntry.arguments?.getInt(NavigationDestination.UserOnboarding.MODE) ?: 0

            UserOnboardingScreen(
                modifier = modifierSNI,
                userViewModel = userViewModel,
                mode = if (mode == 0) UserOnboardingMode.INIT else UserOnboardingMode.EDIT,
                navigateToUserSelect = {
                    navController.navigateUp()
                },
                navigateToMain = {
                    navController.navigate(NavigationDestination.Main.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                },
            )
        }

        composable(route = NavigationDestination.UserProfile.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserProfileScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                navigateToRecipePreferenceSetting = {
                    navController.navigate(NavigationDestination.RecipePreferenceSetting.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToSavedRecipe = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/${0}") {
                        launchSingleTop = true
                    }
                },
                navigationToShoppingList = {
                    navController.navigate(NavigationDestination.ShoppingList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipePreferenceSetting.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipePreferenceSettingScreen(
                modifier = modifierSNI,
                userViewModel = userViewModel,
                navigateToUserProfile = {
                    navController.navigate(NavigationDestination.UserProfile.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToUserOnboarding = {
                    navController.navigate("${NavigationDestination.UserOnboarding.route}/1") {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${NavigationDestination.RecipeHistory.route}/{${NavigationDestination.RecipeHistory.MODE}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeHistory.MODE) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val mode =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeHistory.MODE) ?: 0

            RecipeHistoryScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                recipeHistoryViewModel = recipeHistoryViewModel,
                mode = if (mode == 0) RecipeHistoryType.SavedRecipe else RecipeHistoryType.CompletedFood,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeDetail = { id ->
                    navController.navigate("${NavigationDestination.RecipeDetail.route}/${id}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.ShoppingList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            ShoppingListScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                shoppingListViewModel = shoppingListViewModel,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                },
                navigateToShoppingListAdd = {
                    navController.navigate("${NavigationDestination.ShoppingListAdd.route}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.ShoppingListAdd.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            ShoppingListAddScreen(
                modifier = modifierSNI,
                userViewModel = userViewModel,
                shoppingListViewModel = shoppingListViewModel,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipeList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeListScreen(
                modifier = modifierSN,
                userViewModel = userViewModel,
                recipeViewModel = recipeViewModel,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeChat = {
                    navController.navigate(NavigationDestination.RecipeChat.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeDetail = { recipeInt ->
                    navController.navigate("${NavigationDestination.RecipeDetail.route}/${recipeInt}") {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipeChat.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeChatScreen(
                modifier = modifierSNI,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = "${NavigationDestination.RecipeDetail.route}/{${NavigationDestination.RecipeDetail.RECIPE_ID}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeDetail.RECIPE_ID) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            val recipeId =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeDetail.RECIPE_ID)
                    ?: 0

            RecipeDetailScreen(
                modifier = modifierN,
                recipeViewModel = recipeViewModel,
                recipeId = recipeId,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeStep = { id ->
                    navController.navigate("${NavigationDestination.RecipeStep.route}/${id}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${NavigationDestination.RecipeStep.route}/{${NavigationDestination.RecipeStep.RECIPE_ID}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeStep.RECIPE_ID) { type = NavType.IntType },
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val recipeId =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeStep.RECIPE_ID)
                    ?: 0

            RecipeStepScreen(
                modifier = modifierSN,
                recipeViewModel = recipeViewModel,
                recipeId = recipeId,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeList = {
                    navController.navigate(NavigationDestination.RecipeList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeComplete = { id ->
                    navController.navigate("${NavigationDestination.RecipeComplete.route}/${id}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${NavigationDestination.RecipeComplete.route}/{${NavigationDestination.RecipeComplete.RECIPE_ID}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeComplete.RECIPE_ID) { type = NavType.IntType },
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val recipeId =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeStep.RECIPE_ID)
                    ?: 0

            RecipeCompleteScreen(
                modifier = modifierSN,
                recipeViewModel = recipeViewModel,
                recipeId = recipeId,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeHistory = {
                    navController.navigate("${NavigationDestination.RecipeHistory.route}/${1}") {
                        launchSingleTop = true
                    }
                },
                navigateToIngredientAdd = {
                    navController.navigate(NavigationDestination.IngredientAdd.route) {
                        launchSingleTop = true
                    }
                },
                navigateToIngredientRemove = { id ->
                    navController.navigate("${NavigationDestination.IngredientRemove.route}/${id}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.IngredientAdd.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            IngredientAddScreen(
                modifier = modifierSNI,
                recipeViewModel = recipeViewModel,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = "${NavigationDestination.IngredientRemove.route}/{${NavigationDestination.IngredientRemove.RECIPE_ID}}",
            arguments = listOf(
                navArgument(NavigationDestination.IngredientRemove.RECIPE_ID) { type = NavType.IntType },
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val recipeId =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeStep.RECIPE_ID)
                    ?: 0

            IngredientRemoveScreen(
                modifier = modifierSN,
                recipeViewModel = recipeViewModel,
                recipeId = recipeId,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}