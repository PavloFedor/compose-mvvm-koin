package com.pavlo.fedor.compose.core

import java.text.SimpleDateFormat
import java.util.*

enum class DatePattern(val value: String) {

    ISO_DATE_FORMAT("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    SHORT_DATE("dd/MM/yy");

    operator fun invoke(local: Locale = Locale.getDefault(), date: Date): String = SimpleDateFormat(value, local)
        .apply { timeZone = TimeZone.getTimeZone("GTM+00:00") }
        .format(date)

}
