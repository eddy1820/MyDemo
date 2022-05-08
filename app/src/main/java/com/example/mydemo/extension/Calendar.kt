package com.example.mydemo.extension

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


const val YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd\'T\'HH:mm:ss\'Z\'"
const val YYYY_MM_DD = "yyyy-MM-dd"
const val HH_MM = "HH:mm"


fun Calendar.getFormatStringWithBeginOfDay(): String {
    val format = DecimalFormat("00")
    return "${this.get(Calendar.YEAR)}-${format.format(this.get(Calendar.MONTH) + 1)}-${
        format.format(
            this.get(Calendar.DAY_OF_MONTH)
        )
    }T00:00:00Z"
}

fun Calendar.getFormatString(timeZone: TimeZone): String {
    val sdf =
        SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS_Z, Locale.ENGLISH).apply { this.timeZone = timeZone }
    return sdf.format(this.time)
}