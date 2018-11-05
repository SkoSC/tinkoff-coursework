package com.skosc.rtu.utils

fun cantorPairing(k1: Int, k2: Int): Int {
    return (((k1 + k2) * (k1 + k2 + 1)) / 2) + k2
}