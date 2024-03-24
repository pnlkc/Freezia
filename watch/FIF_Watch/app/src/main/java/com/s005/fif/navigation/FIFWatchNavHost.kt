package com.s005.fif.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.navigation.NavigationDestination.Main
import com.s005.fif.navigation.NavigationDestination.RecipeDetail
import com.s005.fif.navigation.NavigationDestination.RecipeStep
import com.s005.fif.navigation.NavigationDestination.ShoppingList
import com.s005.fif.navigation.NavigationDestination.TimerDetail
import com.s005.fif.navigation.NavigationDestination.TimerList
import com.s005.fif.navigation.NavigationDestination.Warning
import com.s005.fif.recipe.ui.step.RecipeStepScreen
import com.s005.fif.recipe.ui.detail.RecipeDetailScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen
import com.s005.fif.timer.ui.TimerDetailScreen
import com.s005.fif.timer.ui.TimerListScreen
import com.s005.fif.utils.AlarmUtil
import com.s005.fif.warning.ui.WarningScreen

@Composable
fun FIFWatchNavHost(
    navController: NavHostController
) {
    val context = LocalContext.current

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Main.route,
    ) {
        composable(route = Main.route) {
            MainScreen(
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
                },
                navigateToWarning =  {
                    navController.navigate(Warning.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = ShoppingList.route
        ) {
            ShoppingListScreen(

            )
        }

        composable(route = RecipeDetail.route) {
            RecipeDetailScreen(
                navigateToRecipeStep = {
                    navController.navigate(RecipeStep.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = RecipeStep.route) {
            RecipeStepScreen(
                navigateToMain = {
                    navController.popBackStack(route = Main.route, inclusive = false)
                }
            )
        }

        composable(route = Warning.route) {
            WarningScreen(

            )
        }

        composable(route = TimerList.route) {
            TimerListScreen(
                navigateToTimerDetail = {
                    navController.navigate(TimerDetail.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = TimerDetail.route) {
            TimerDetailScreen(
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeDetail = {

                },
                navigateToTimerDone = {
                    AlarmUtil.setAlarm(context = context, delayMillis = 5000L, 1)
                    AlarmUtil.setAlarm(context = context, delayMillis = 10000L, 2)
                    AlarmUtil.setAlarm(context = context, delayMillis = 15000L, 3)
                }
            )
        }
    }
}