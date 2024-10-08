package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(
    val result: String,
    val memberList: List<UserItemResponse>
)
