package com.skosc.tkffintech.misc

import androidx.annotation.ColorRes
import com.skosc.tkffintech.R
import java.util.*

object ChipColors {
    private val allAllowedColors = arrayOf(
            R.color.event_chip_blue,
            R.color.event_chip_green,
            R.color.event_chip_red,
            R.color.event_chip_purple
    )

    @ColorRes
    fun randomColor(): Int {
        val rnd = Random()
        val randomColorId = rnd.nextInt(3)
        return allAllowedColors[randomColorId]
    }

    @ColorRes
    fun colorForName(name: String): Int {
        return when (name.trim().toLowerCase()) {
            "purple" -> R.color.event_chip_purple
            "green" -> R.color.event_chip_green
            "orange" -> R.color.event_chip_red
            else -> randomColor()
        }
    }
}