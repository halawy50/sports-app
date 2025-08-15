package com.example.myapplication.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatIsoDateToLocalShort(dateString: String): String {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    isoFormat.timeZone = TimeZone.getTimeZone("UTC")

    val localDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    localDateFormat.timeZone = TimeZone.getDefault()

    val date = isoFormat.parse(dateString)
    return localDateFormat.format(date!!)
}
