package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    val onboardYn: Boolean,
    val name: String,
    val imgUrl: String,
    val stress: Int,
    val bloodOxygen: Int,
    val sleep: Int,
    val preferMenu: String,
    val diseases: List<Int>,
    val dislikeIngredients: List<Int>,
    val fridgeId: Int
)
