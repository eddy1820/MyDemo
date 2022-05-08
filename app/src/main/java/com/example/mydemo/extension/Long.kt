package com.example.mydemo.extension

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Long.toDateStr(format: String, timeZone: TimeZone = TimeZone.getDefault()): String =
    Date(this).toStr(format, timeZone)

fun Long.toDateStr(simpleDateFormat: SimpleDateFormat): String =
    Date(this).let<Date, String>(simpleDateFormat::format)

fun Long.isZero(): Boolean = this == 0L

fun Long.isToday(): Boolean =
    System.currentTimeMillis()
        .toDateStr(DateTime.YYYYMMDD_DASH) == this.toDateStr(DateTime.YYYYMMDD_DASH)

fun Long.isFuture(): Boolean =
    this >= System.currentTimeMillis()

fun Long.in24H(): Boolean =
    abs(System.currentTimeMillis() - this) <= 86400000L

fun Long.inLast7Days(): Boolean =
    abs(System.currentTimeMillis() - this) <= 86400000 * 7L

fun Long.isSameDayWith(time: Long?): Boolean =
    this.toDateStr(DateTime.YYYYMMDD_DASH) == time?.toDateStr(DateTime.YYYYMMDD_DASH)

fun Long.toAmountWithoutDot(): String = this.toString().toAmountWithoutDot()