package com.s005.fif.utils

object StringLineChangeUtil {
    private const val REGULAR_SPACE_CHARACTER = ' '
    private const val NON_BREAKABLE_SPACE_UNICODE = '\u00A0'

    fun String?.toNonBreakingString() = this.orEmpty()
        .replace(
            REGULAR_SPACE_CHARACTER,
            NON_BREAKABLE_SPACE_UNICODE
        )
}