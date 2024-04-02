package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class MemberSelectRequest(
    val memberId: Int
)
