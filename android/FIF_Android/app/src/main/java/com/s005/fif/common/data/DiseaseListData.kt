package com.s005.fif.common.data


data class DiseaseItemData(
    val diseaseId: Int,
    val name: String
)
object DiseaseListData {
    val list = listOf(
        DiseaseItemData(1, "당뇨"),
        DiseaseItemData(2, "비만"),
        DiseaseItemData(3, "고혈압"),
        DiseaseItemData(4, "역류성 식도염"),
        DiseaseItemData(5, "만성 소화불량"),
        DiseaseItemData(6, "관절염"),
        DiseaseItemData(7, "골다공증"),
        DiseaseItemData(8, "변비"),
        DiseaseItemData(9, "빈혈"),
        DiseaseItemData(10, "통풍"),
    )

    val map = list.associate { it.diseaseId to it.name }

    val mapIdToItem = list.associateBy { it.diseaseId }
}