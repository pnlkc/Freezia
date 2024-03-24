package com.s005.fif.recipe.ui

import androidx.lifecycle.ViewModel
import com.s005.fif.recipe.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

}