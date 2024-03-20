package com.s005.fif.ui.navigation

import android.app.Activity
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.recipe.ui.chat.RecipeChatScreen
import com.s005.fif.recipe.ui.detail.RecipeDetailScreen
import com.s005.fif.recipe.ui.list.RecipeListScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen
import com.s005.fif.user.ui.onboarding.UserOnboardingScreen
import com.s005.fif.user.ui.profile.RecipePreferenceSettingScreen
import com.s005.fif.user.ui.profile.UserProfileScreen
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryScreen
import com.s005.fif.user.ui.select.UserSelectScreen

@Composable
fun FIFNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.UserSelect.route,
        modifier = modifier,
    ) {
        composable(route = NavigationDestination.Main.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            MainScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
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
                navigateToRecipeDetail = {
                    navController.navigate(NavigationDestination.RecipeDetail.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserSelect.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserSelectScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateToUserOnboarding = {
                    navController.navigate(NavigationDestination.UserOnboarding.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.UserOnboarding.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserOnboardingScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
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
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserProfileScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateToRecipePreferenceSetting = {
                    navController.navigate(NavigationDestination.RecipePreferenceSetting.route) {
                        launchSingleTop = true
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToSavedRecipe = {
                    navController.navigate(NavigationDestination.RecipeHistory.route) {
                        launchSingleTop = true
                    }
                },
                navigationToShoppingList = {
                    navController.navigate(NavigationDestination.ShoppingList.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipePreferenceSetting.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipePreferenceSettingScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
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

        composable(route = NavigationDestination.RecipeHistory.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeHistoryScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.ShoppingList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            ShoppingListScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.RecipeList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeListScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeChat = {
                    navController.navigate(NavigationDestination.RecipeChat.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeDetail = {
                    navController.navigate(NavigationDestination.RecipeDetail.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipeChat.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeChatScreen(
                modifier = modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.RecipeDetail.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            RecipeDetailScreen(
                modifier = modifier,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}