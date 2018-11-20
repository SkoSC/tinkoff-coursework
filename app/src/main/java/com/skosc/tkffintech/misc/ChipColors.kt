package com.skosc.tkffintech.misc

import androidx.annotation.ColorRes
import com.skosc.tkffintech.R
import java.util.*

/**
 * Helper methods helping to resolve colors for chips
 */
object ChipColors {
    private val allAllowedColors = arrayOf(
            R.color.event_chip_blue,
            R.color.event_chip_green,
            R.color.event_chip_red,
            R.color.event_chip_purple
    )

    /**
     * Color of chip based on color's name passed as argument. If color not found fallbacks to
     * random one
     */
    @ColorRes
    fun colorForName(name: String): Int {
        return when (name.trim().toLowerCase()) {
            "purple" -> R.color.event_chip_purple
            "green" -> R.color.event_chip_green
            "orange" -> R.color.event_chip_red
            else -> randomColor()
        }
    }

    @ColorRes
    private fun randomColor(): Int {
        val rnd = Random()
        val randomColorId = rnd.nextInt(3)
        return allAllowedColors[randomColorId]
    }
}