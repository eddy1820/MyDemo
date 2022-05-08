package com.example.mydemo.extension

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object DateTime {
    const val TAG = "DateTime.kt"

    const val YYYYMMDDHHMMSS_DASH = "yyyy-MM-dd HH:mm:ss"
    const val MMDDHHMMSS_DASH = "MM-dd HH:mm:ss"
    const val YYYYMMDDhhMMSS_DASH = "yyyy-MM-dd hh:mm:ss"
    const val YYYYMMDDHHMM_DASH = "yyyy-MM-dd HH:mm"
    const val YYYYMMDDHHMM_SLASH = "yyyy/MM/dd HH:mm"
    const val HHMMSSYYYYMMDD_SLASH = "HH:mm:ss yyyy/MM/dd"
    const val HHMMSSYYYYMMDD_DASH = "HH:mm:ss yyyy-MM-dd"
    const val YYYYMMDD_SLASH = "yyyy/MM/dd"
    const val YYYYMM_SLASH = "yyyy/MM"
    const val YYYYMMDD_DASH = "yyyy-MM-dd"
    const val YYYYMMDD = "yyyyMMdd"
    const val YYYYMM_DASH = "yyyy-MM"
    const val YYYY = "yyyy"
    const val M = "M"
    const val dd = "dd"
    const val YYYYMMDDHHMMSS_SLASH = "yyyy/MM/dd HH:mm:ss"
    const val YYYYMMDDhhMMSS_SLASH = "yyyy/MM/dd hh:mm:ss"
    const val MMDDHHMM_SLASH = "MM/dd HH:mm"
    const val MMDDHHMM_DASH = "MM-dd HH:mm"
    const val MMDD_DASH = "MM-dd"
    const val HHMM = "HH:mm"
    const val HH = "HH"
    const val mm = "mm"
    const val mmss = "mm:ss"
    const val hhmmss = "HH:mm:ss"
    const val MM_DD_IN_CHINESE = "MM月dd日"
    const val MM_DD_DASH = "MM-dd"
    const val M_D_IN_CHINESE = "M月d日"


    //
    val FORMAT_YYYYMM_DASH by lazy {
        SimpleDateFormat(YYYYMM_DASH, Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    // formats from server
    val FORMAT_YYYYMMDDHHMMSS_DASH by lazy {
        SimpleDateFormat(
            YYYYMMDDHHMMSS_DASH,
            Locale.US
        ).apply { timeZone = TimeZone.getDefault() }
    }
    val FORMAT_YYYYMMDDHHMM_DASH by lazy {
        SimpleDateFormat(
            YYYYMMDDHHMM_DASH,
            Locale.US
        ).apply { timeZone = TimeZone.getDefault() }
    }
    val FORMAT_YYYYMMDD_DASH by lazy {
        SimpleDateFormat(YYYYMMDD_DASH, Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_HHMM by lazy {
        SimpleDateFormat(HHMM, Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    // new format form https://innotech.atlassian.net/browse/QIP-6822?focusedCommentId=52729
    val FORMAT_YEAR_DATE_TIME_DASH by lazy {
        SimpleDateFormat(
            YYYYMMDDHHMMSS_DASH,
            Locale.US
        ).apply { timeZone = TimeZone.getDefault() }
    }

    // for language zh
    val FORMAT_YEAR_DATE_TIME_ZH by lazy {
        SimpleDateFormat(
            "yyyy年MM月dd日 HH:mm",
            Locale.US
        ).apply { timeZone = TimeZone.getDefault() }
    }
    val FORMAT_YEAR_DATE_ZH by lazy {
        SimpleDateFormat("yyyy年MM月dd日", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_YEAR_MONTH_ZH by lazy {
        SimpleDateFormat("yyyy年MM月", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE_TIME_ZH by lazy {
        SimpleDateFormat("dd-MM HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE_ZH by lazy {
        SimpleDateFormat("dd-MM", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    // for language ja
    val FORMAT_DATE_TIME_JA by lazy {
        SimpleDateFormat("MM月dd日 HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE_JA by lazy {
        SimpleDateFormat("MM月dd日", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    // for language ko
    val FORMAT_DATE_TIME_KO by lazy {
        SimpleDateFormat("MM월dd일 HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE_KO by lazy {
        SimpleDateFormat("MM월dd일", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    // for languages NOT zh
    val FORMAT_YEAR_DATE_TIME by lazy {
        SimpleDateFormat(
            "dd MMM yyyy HH:mm",
            Locale.US
        ).apply { timeZone = TimeZone.getDefault() }
    }
    val FORMAT_YEAR_DATE by lazy {
        SimpleDateFormat("dd MMM yyyy", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_YEAR_MONTH by lazy {
        SimpleDateFormat("MMM yyyy", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE_TIME by lazy {
        SimpleDateFormat("dd MMM HH:mm", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val FORMAT_DATE by lazy {
        SimpleDateFormat("dd MMM", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    fun getDateStrByFormat(fromFormat: String, toFormat: String, target: String): String? =
        try {
            val sdf = SimpleDateFormat(fromFormat, Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            sdf.parse(target)?.let {
                SimpleDateFormat(toFormat, Locale.getDefault()).format(it)
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(
                e.message,
                "[getDateStrByFormat]: $target parse failed. fromFormat=${fromFormat.toPattern()} toFormat=${toFormat.toPattern()}"
            )
            null
        }


    fun getDateStrByFormat(
        fromFormat: SimpleDateFormat,
        toFormat: SimpleDateFormat,
        target: String
    ): String? =
        try {
            fromFormat.parse(target)?.let {
                toFormat.format(it)
            }
        } catch (t: Throwable) {
            Timber.tag(TAG).e(
                t,
                "[getDateStrByFormat]: $target parse failed. fromFormat=${fromFormat.toPattern()} toFormat=${toFormat.toPattern()}"
            )
            null
        }

    fun getDateByFormat(
        fromFormat: String,
        target: String,
        timeZone: TimeZone = TimeZone.getDefault()
    ): Date? =
        SimpleDateFormat(fromFormat, Locale.getDefault()).let {
            it.timeZone = timeZone
            it.parse(target)
        }
}

