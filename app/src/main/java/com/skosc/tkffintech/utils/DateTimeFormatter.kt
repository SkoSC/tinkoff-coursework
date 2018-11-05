package com.skosc.tkffintech.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

object DateTimeFormatter {
    val DATE_TIME_FORMATTER_FULL: DateTimeFormatter get() = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val DATE_FORMATTER_SHORT_EU: DateTimeFormatter get() = DateTimeFormat.forPattern("dd-MM-yyyy")
}