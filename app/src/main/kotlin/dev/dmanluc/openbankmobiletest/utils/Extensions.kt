package dev.dmanluc.openbankmobiletest.utils

import java.security.MessageDigest
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

fun String.hashMd5(): String {
    val hexCharacters = "0123456789abcdef"
    val digest = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return digest.joinToString(separator = "",
        transform = { a ->
            String(
                charArrayOf(
                    hexCharacters[a.toInt() shr 4 and 0x0f],
                    hexCharacters[a.toInt() and 0x0f]
                )
            )
        })
}

fun Boolean?.orFalse(): Boolean = this ?: false