package com.s005.fif.main.ui

import com.s005.fif.main.dto.MemberResponse

data class MainUiState(
    var member: Member? = null
)

data class Member(
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

fun MemberResponse.toMember() = Member(
    onboardYn, name, imgUrl, stress, bloodOxygen, sleep, preferMenu, diseases, dislikeIngredients, fridgeId
)