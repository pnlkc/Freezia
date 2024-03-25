package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRequest(
    val preferMenu: String,
    val diseases: List<Int>,
    val dislikeIngredients: List<Int>
)

data class Onboarding(
    val preferMenu: String = "",
    val diseases: List<Int> = listOf(),
    val dislikeIngredients: List<Int> = listOf()
)

fun Onboarding.toOnboardingRequest() = OnboardingRequest(
    preferMenu, diseases, dislikeIngredients
)
