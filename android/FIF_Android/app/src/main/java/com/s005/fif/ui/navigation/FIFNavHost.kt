package com.s005.fif.ui.navigation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.s005.fif.main.ui.MainScreen
import com.s005.fif.recipe.ui.chat.RecipeChatScreen
import com.s005.fif.recipe.ui.complete.IngredientAddScreen
import com.s005.fif.recipe.ui.complete.IngredientRemoveScreen
import com.s005.fif.recipe.ui.complete.RecipeCompleteScreen
import com.s005.fif.recipe.ui.detail.RecipeDetailScreen
import com.s005.fif.recipe.ui.list.RecipeListScreen
import com.s005.fif.recipe.ui.step.RecipeStepScreen
import com.s005.fif.shopping_list.ui.ShoppingListScreen
import com.s005.fif.user.ui.UserViewModel
import com.s005.fif.user.ui.onboarding.UserOnboardingScreen
import com.s005.fif.user.ui.profile.RecipePreferenceSettingScreen
import com.s005.fif.user.ui.profile.UserProfileScreen
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryScreen
import com.s005.fif.user.ui.recipe_history.ui.RecipeHistoryType
import com.s005.fif.user.ui.select.UserSelectScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FIFNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoginUser: Boolean,
    userViewModel: UserViewModel = hiltViewModel(),
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
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        userViewModel.getMemberInfo()
    }

    NavHost(
        navController = navController,
        startDestination = if (!isLoginUser || userViewModel.memberInfo == null || !userViewModel.memberInfo!!.onboardYn) {
            NavigationDestination.UserSelect.route
        } else {
            NavigationDestination.Main.route
        },
        modifier = modifier,
    ) {
        composable(route = NavigationDestination.Main.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            MainScreen(
                modifier = modifierSN,
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
                modifier = modifierSN,
                navigateToUserOnboarding = {
                    navController.navigate(NavigationDestination.UserOnboarding.route) {
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

        composable(route = NavigationDestination.UserOnboarding.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            UserOnboardingScreen(
                modifier = modifierSNI,
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
                modifier = modifierSN,
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
                }
            )
        }

        composable(route = NavigationDestination.RecipePreferenceSetting.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipePreferenceSettingScreen(
                modifier = modifierSNI,
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

        composable(
            route = "${NavigationDestination.RecipeHistory.route}/{${NavigationDestination.RecipeHistory.MODE}}",
            arguments = listOf(
                navArgument(NavigationDestination.RecipeHistory.MODE) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            val mode =
                navBackStackEntry.arguments?.getInt(NavigationDestination.RecipeHistory.MODE) ?: 0

            Log.d("로그", " - FIFNavHost() 호출됨 / mode: $mode")

            RecipeHistoryScreen(
                modifier = modifierSN,
                navigateUp = {
                    navController.navigateUp()
                },
                mode = if (mode == 0) RecipeHistoryType.SavedHistory else RecipeHistoryType.CompletedFood
            )
        }

        composable(route = NavigationDestination.ShoppingList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            ShoppingListScreen(
                modifier = modifierSN,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.RecipeList.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeListScreen(
                modifier = modifierSN,
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
                modifier = modifierSNI,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.RecipeDetail.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false

            RecipeDetailScreen(
                modifier = modifierN,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeStep = {
                    navController.navigate(NavigationDestination.RecipeStep.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipeStep.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeStepScreen(
                modifier = modifierSN,
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToRecipeList = {
                    navController.navigate(NavigationDestination.RecipeList.route) {
                        launchSingleTop = true
                    }
                },
                navigateToRecipeComplete = {
                    navController.navigate(NavigationDestination.RecipeComplete.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.RecipeComplete.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            RecipeCompleteScreen(
                modifier = modifierSN,
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
                navigateToIngredientRemove = {
                    navController.navigate(NavigationDestination.IngredientRemove.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = NavigationDestination.IngredientAdd.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            IngredientAddScreen(
                modifier = modifierSNI,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = NavigationDestination.IngredientRemove.route) {
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

            IngredientRemoveScreen(
                modifier = modifierSN,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}