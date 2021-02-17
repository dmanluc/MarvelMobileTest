package dev.dmanluc.openbankmobiletest.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.fromUTCTimeToDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    return try {
        dateFormat.parse(this)
    } catch (parseException: ParseException) {
        null
    }
}