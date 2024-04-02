package com.s005.fif.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserItemResponse(
    val memberId: Int,
    val name: String,
    val imgUrl: String
)

data class UserItem(
    val memberId: Int,
    val name: String,
    val imgUrl: String
)

fun UserItemResponse.toUserItem() = UserItem(memberId, name, imgUrl)