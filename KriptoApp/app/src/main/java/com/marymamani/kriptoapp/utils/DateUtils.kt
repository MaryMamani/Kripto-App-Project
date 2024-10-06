package com.marymamani.kriptoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"

    fun formatTimestamp(timestamp: Long?, format: String = DEFAULT_DATE_FORMAT): String {
        return if (timestamp != null) {
            val date = Date(timestamp)
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.format(date)
        } else {
            "00/00/0000"
        }
    }
}