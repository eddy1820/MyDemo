package com.example.mydemo.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.apiDateFormat(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(this.time)
}

@SuppressLint("SimpleDateFormat")
fun Date.tabDateFormat(): String {
    val simpleDateFormat = SimpleDateFormat("MM月dd日\n E", Locale.CHINA)
//  simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC").getUSEasternTime()
    return simpleDateFormat.format(this.time)
}

@SuppressLint("SimpleDateFormat")
fun Date.tabApiDateFormat(): String {
    val simpleDateFormat = SimpleDateFormat("yyyyMMdd")
//  simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC").getUSEasternTime()
    return simpleDateFormat.format(this.time)
}

@SuppressLint("SimpleDateFormat")
fun Date.tabApiDateCompleteFormat(): String {
    val simpleDateFormat = SimpleDateFormat(DateTime.YYYYMMDD_DASH)
//  simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC").getUSEasternTime()
    return simpleDateFormat.format(this.time)
}

fun Date.toStr(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String =
    SimpleDateFormat(dateFormat, Locale.getDefault()).let {
        it.timeZone = timeZone
        it.format(this)
    }

fun Date.isToday() = System.currentTimeMillis().toDateStr(DateTime.YYYYMMDD_DASH) == this.toStr(
    DateTime.YYYYMMDD_DASH
)

fun Date.isSameDayWith(date: Date?) =
    this.toStr(DateTime.YYYYMMDD_DASH) == date?.toStr(DateTime.YYYYMMDD_DASH)

//fun Date.getWeekDayStr(context: Context) = Calendar.getInstance().let {
//  it.time = this
//  when(it.get(Calendar.DAY_OF_WEEK)){
//    1 -> R.string.sunday1
//    2 -> R.string.monday1
//    3 -> R.string.tuesday1
//    4 -> R.string.wednesday1
//    5 -> R.string.thursday1
//    6 -> R.string.friday1
//    7 -> R.string.saturday1
//    else -> null
//  }?.let(context::getString)
//}
