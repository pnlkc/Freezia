package com.s005.fif.fcm

import androidx.lifecycle.MutableLiveData

object RecipeLiveData {
    val isRecipeConnected = MutableLiveData(false)
    val recipeStep = MutableLiveData(0)
    var recipeData: RecipeData? = null
}