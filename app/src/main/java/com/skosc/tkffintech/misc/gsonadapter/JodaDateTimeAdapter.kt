package com.skosc.tkffintech.misc.gsonadapter

import org.joda.time.DateTime
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import com.google.gson.JsonSerializer
import com.google.gson.JsonDeserializer
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.reflect.Type


class JodaDateTimeAdapter : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
    companion object {
        internal val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    override fun deserialize(je: JsonElement, type: Type,
                    jdc: JsonDeserializationContext): DateTime? {
        return if (je.asString.isEmpty()) null else DATE_TIME_FORMATTER.parseDateTime(je.asString)
    }

    override fun serialize(src: DateTime?, typeOfSrc: Type,
                  context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(if (src == null) "" else DATE_TIME_FORMATTER.print(src))
    }
}