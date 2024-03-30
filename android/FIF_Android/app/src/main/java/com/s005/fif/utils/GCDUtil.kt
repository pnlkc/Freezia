package com.s005.fif.utils

object GCDUtil {
    fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}