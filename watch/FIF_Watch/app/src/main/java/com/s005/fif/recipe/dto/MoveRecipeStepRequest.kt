package com.s005.fif.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoveRecipeStepRequest(
    val sender: Int = 2
)
