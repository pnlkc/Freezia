package com.s005.fif.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.s005.fif.main.MainScreen
import com.s005.fif.user.ui.onboarding.UserOnboardingScreen
import com.s005.fif.user.ui.profile.RecipePreferenceSettingScreen
import com.s005.fif.user.ui.profile.UserProfileScreen
import com.s005.fif.user.ui.select.UserSelectScreen

@Composable
fun FIFNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.UserSelect.route,
        modifier = modifier,
    ) {
        composable(route = NavigationDestination.Main.route) {
            MainScreen(
                navigateToUserProfile = {
                    navController.navigate(NavigationDestination.UserProfile.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserSelect.route) {
            UserSelectScreen(
                modifier = modifier,
                navigateToUserOnboarding = {
                    navController.navigate(NavigationDestination.UserOnboarding.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserOnboarding.route) {
            UserOnboardingScreen(
                modifier = modifier,
                navigateToUserSelect = {
                    navController.navigateUp()
                },
                navigateToMain = {
                    navController.navigate(NavigationDestination.Main.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserProfile.route) {
            UserProfileScreen(
                modifier = modifier,
                navigateToRecipePreferenceSetting = {
                    navController.navigate(NavigationDestination.RecipePreferenceSetting.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.RecipePreferenceSetting.route) {
            RecipePreferenceSettingScreen(
                modifier = modifier,
                navigateToUserProfile = {
                    navController.navigate(NavigationDestination.UserProfile.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}