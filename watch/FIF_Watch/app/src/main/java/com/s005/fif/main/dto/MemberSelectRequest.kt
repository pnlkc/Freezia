package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class MemberSelectRequest(
    val memberId: Int
)
