package com.skosc.tkffintech.misc.gsonadapter

import org.joda.time.DateTime
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonSerializer
import com.google.gson.JsonDeserializer
import com.skosc.tkffintech.utils.DateTimeFormatter.DATE_TIME_FORMATTER_FULL
import java.lang.reflect.Type


class JodaDateTimeAdapter : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
    override fun deserialize(je: JsonElement, type: Type,
                    jdc: JsonDeserializationContext): DateTime? {
        return if (je.asString.isEmpty()) null else DATE_TIME_FORMATTER_FULL.parseDateTime(je.asString)
    }

    override fun serialize(src: DateTime?, typeOfSrc: Type,
                  context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(if (src == null) "" else DATE_TIME_FORMATTER_FULL.print(src))
    }
}