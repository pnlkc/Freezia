package com.s005.fif.user.ui.select

import com.s005.fif.user.dto.Member
import com.s005.fif.user.dto.UserItem

data class UserSelectUiState(
    val userList: List<UserItem> = listOf(),
    val memberInfo: Member? = null
)
