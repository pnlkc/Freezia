package com.s005.fif.main.dto

import kotlinx.serialization.Serializable

@Serializable
data class MemberInfoResponse(
    val result: String,
    val member: MemberResponse
)
