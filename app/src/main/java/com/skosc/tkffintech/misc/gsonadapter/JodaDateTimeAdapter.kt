package com.skosc.tkffintech.misc.gsonadapter

import com.google.gson.*
import com.skosc.tkffintech.utils.DateTimeFormatter.DATE_TIME_FORMATTER_FULL
import org.joda.time.DateTime
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