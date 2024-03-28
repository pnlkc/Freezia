package com.s005.fif.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.main.ui.MainViewModel
import com.s005.fif.navigation.NavigationDestination.Main
import com.s005.fif.navigation.NavigationDestination.RecipeDetail
import com.s005.fif.navigation.NavigationDestination.RecipeStep
import com.s005.fif.navigation.NavigationDestination.ShoppingList
import com.s005.fif.navigation.NavigationDestination.TimerDetail
import com.s005.fif.navigation.NavigationDestination.TimerList
import com.s005.fif.navigation.NavigationDestination.Warning
import com.s005.fif.recipe.ui.RecipeViewModel
import com.s005.fif.recipe.ui.step.RecipeStepScreen
import com.s005.fif.recipe.ui.detail.RecipeDetailScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen
import com.s005.fif.shopping_list.ui.ShoppingListViewModel
import com.s005.fif.timer.ui.TimerDetailScreen
import com.s005.fif.timer.ui.TimerListScreen
import com.s005.fif.timer.ui.TimerViewModel
import com.s005.fif.utils.AlarmUtil
import com.s005.fif.warning.ui.WarningScreen

@Composable
fun FIFWatchNavHost(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    val shoppingListViewModel: ShoppingListViewModel = hiltViewModel()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Main.route,
    ) {
        composable(route = Main.route) {
            MainScreen(
                mainViewModel = mainViewModel,
                navigateToShoppingList = {
                    navController.navigate(ShoppingList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeDetail = {
                    navController.navigate(RecipeDetail.route) {
                        launchSingleTop = true
                    }
                },
                navigateToTimerList = {
                    navController.navigate(TimerList.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = ShoppingList.route
        ) {
            ShoppingListScreen(
                shoppingListViewModel = shoppingListViewModel
            )
        }

        composable(route = RecipeDetail.route) {
            RecipeDetailScreen(
                navigateToRecipeStep = {
                    navController.navigate("${RecipeStep.route}/0") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${RecipeStep.route}/{${RecipeStep.STEP}}",
            arguments = listOf(
                navArgument(RecipeStep.STEP) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val step = navBackStackEntry.arguments?.getInt(RecipeStep.STEP) ?: 0

            RecipeStepScreen(
                recipeViewModel = recipeViewModel,
                timerViewModel = timerViewModel,
                navigateToMain = {
                    navController.popBackStack(route = Main.route, inclusive = false)
                },
                navigateToTimerDetail = { idx ->
                    navController.navigate("${TimerDetail.route}/${idx}") {
                        launchSingleTop = false
                        restoreState = true
                    }
                },
                step = step
            )
        }

        composable(route = Warning.route) {
            WarningScreen()
        }

        composable(route = TimerList.route) {
            TimerListScreen(
                timerViewModel = timerViewModel,
                navigateToTimerDetail = { idx ->
                    navController.navigate("${TimerDetail.route}/${idx}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "${TimerDetail.route}/{${TimerDetail.TIMER_IDX}}",
            arguments = listOf(
                navArgument(TimerDetail.TIMER_IDX) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val idx = navBackStackEntry.arguments?.getInt(TimerDetail.TIMER_IDX) ?: 0

            TimerDetailScreen(
                timerViewModel = timerViewModel,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeDetail = { step ->
                    navController.navigate("${RecipeStep.route}/${step}") {
                        launchSingleTop = true
                    }
                },
                navigateToTimerList = {
                    navController.navigate(TimerList.route) {
                        launchSingleTop = true
                    }
                },
                idx = idx
            )
        }
    }
}