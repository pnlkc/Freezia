package com.s005.fif.common.data


data class LikeFoodItemData(
    val foodId: Int,
    val name: String
)
object LikeFoodListData {
    val list = listOf(
        LikeFoodItemData(1, "한식"),
        LikeFoodItemData(2, "양식"),
        LikeFoodItemData(3, "중식"),
        LikeFoodItemData(4, "일식"),
        LikeFoodItemData(5, "밑반찬"),
        LikeFoodItemData(6, "면요리"),
        LikeFoodItemData(7, "볶음요리"),
        LikeFoodItemData(8, "찜요리"),
        LikeFoodItemData(9, "국물요리"),
    )
}