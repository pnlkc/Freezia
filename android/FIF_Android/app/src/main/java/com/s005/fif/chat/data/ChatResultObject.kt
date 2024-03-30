package com.s005.fif.chat.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ChatResultObject {
    var chatResult by mutableStateOf("")

    // TODO. 실제 레시피 이미지로 변경 필요
    var defaultRecipeImg = "https://static.wtable.co.kr/image/production/service/recipe/1067/520ba5e6-5495-4659-b065-09d6fa42352e.jpg?size=500x500"
}