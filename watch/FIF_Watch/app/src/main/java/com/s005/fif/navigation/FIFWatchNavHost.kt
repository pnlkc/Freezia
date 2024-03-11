package com.s005.fif.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.s005.fif.navigation.NavigationDestination.Main
import com.s005.fif.navigation.NavigationDestination.ShoppingList
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen

@Composable
fun FIFWatchNavHost(
    navController: NavHostController
) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Main.route
    ) {
        composable(route = Main.route) {
            MainScreen(

            )
        }

        composable(route = ShoppingList.route) {
            ShoppingListScreen(

            )
        }
    }
}