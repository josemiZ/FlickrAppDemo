package com.josemiz.flickrapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertLongToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    return format.format(date)
}
