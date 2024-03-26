package com.s005.fif.user.dto

import com.s005.fif.common.data.DiseaseItemData
import com.s005.fif.common.data.IngredientItemData
import com.s005.fif.common.data.LikeFoodItemData
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRequest(
    val preferMenu: String,
    val diseases: List<Int>,
    val dislikeIngredients: List<Int>,
)

data class Onboarding(
    val preferMenu: List<LikeFoodItemData> = listOf(),
    val diseases: List<DiseaseItemData> = listOf(),
    val dislikeIngredients: List<IngredientItemData> = listOf(),
)

fun Onboarding.toOnboardingRequest() = OnboardingRequest(
    preferMenu.sortedBy { it.foodId }.joinToString(" ") { it.name },
    diseases.map { it.diseaseId }.sorted(),
    dislikeIngredients.map { it.ingredientId }.sorted()
)
