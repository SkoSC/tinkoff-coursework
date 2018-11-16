package com.skosc.rtu.utils

/**
 * Hashes to ints together.
 * See: https://en.wikipedia.org/wiki/Pairing_function
 */
fun cantorPairing(k1: Int, k2: Int): Int {
    return (((k1 + k2) * (k1 + k2 + 1)) / 2) + k2
}