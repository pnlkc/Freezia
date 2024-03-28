package com.s005.fif.fcm

import androidx.lifecycle.MutableLiveData
import com.s005.fif.fcm.dto.RecipeData

object RecipeLiveData {
    val isRecipeConnected = MutableLiveData(false)
    val recipeStep = MutableLiveData(-1)
    var recipeData: RecipeData? = null
    var isFcmNotification = false
}