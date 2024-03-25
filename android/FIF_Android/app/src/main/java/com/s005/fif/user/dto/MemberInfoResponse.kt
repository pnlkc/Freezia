package com.s005.fif.user.dto


import kotlinx.serialization.Serializable

@Serializable
data class MemberInfoResponse(
    val result: String,
    val member: MemberResponse
)
