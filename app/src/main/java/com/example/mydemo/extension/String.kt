package com.example.mydemo.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Log
import com.example.mydemo.R
import com.example.mydemo.utils.RegexString
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import timber.log.Timber
import java.lang.Double.parseDouble
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * 是否為全數字包含小數點
 */
fun String.isNumber(): Boolean {
    return Pattern.compile(RegexString.NUMBER).matcher(this).matches()
}

/**
 * 是否符合帳號規則
 */
fun String.isAccountRule(): Boolean {
    return Pattern.compile(RegexString.ACCOUNT).matcher(this).matches()
}

/**
 * 是否符合地址規則
 */
fun String.isCurrencyAddressRule(): Boolean {
    return Pattern.compile(RegexString.CURRENCY_ADDRESS).matcher(this).matches()
}

/**
 * 是否符合密碼規則(非註冊)
 */
fun String.isPwdRule(): Boolean {
    return Pattern.compile(RegexString.PASSWORD).matcher(this).matches()
}

/**
 * 是否符合提款密碼規則
 */
fun String.isWithdrawalRule(): Boolean {
    return Pattern.compile("^[0-9]{6}\$").matcher(this).matches()
}

fun String.isMatchesPattern(pattern: String): Boolean =
    try {
        Log.e("pattern ", pattern)
        Pattern.compile(pattern).matcher(this).matches()
    } catch (e: Exception) {
        false
    }

/**
 * 是否符合email規則
 */
fun String.isEmailRule(): Boolean {
    return Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")
        .matcher(this).matches()
}

fun isValidDepositMethodNameRule(method: String) =
    !method.contains("  ") && Pattern.compile(RegexString.DEPOSIT_METHOD_NAME).matcher(method)
        .matches()

/**
 *  加密
 */
fun String.encrypt(): String {
    val key = 87
    val encryptArr = this.toCharArray().map { it.toInt() xor key }
    return Base64.encodeToString(
        String(encryptArr.map { it.toChar() }.toCharArray()).toByteArray(),
        Base64.NO_WRAP
    )
}

fun String.base64Encode(): String = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)

fun String.base64Decode(): String =
    String(Base64.decode(this, Base64.NO_WRAP).map { it.toChar() }.toCharArray())

/**
 * 【全為英文】返回true  否則false
 */
fun String.isEnglish(): Boolean {
    return this.matches("[a-zA-Z]+".toRegex())
}

/**
 * 【全為中文不分繁體或簡體】返回true  否則false
 */
fun String.isChinese(): Boolean = this.matches("[\\u4E00-\\u9FA5]+".toRegex())

fun String.isChinese1To10(): Boolean = matches(RegexString.CHINESE_1_10.toRegex())
fun String.isChinese2To10(): Boolean = isMatchesPattern(RegexString.CHINESE_2_10)
fun String.isChinese1To20WithSymbol(): Boolean =
    matches(RegexString.NAME_CAN_START_WITH_SYMBOL.toRegex())


/**
 * // 金額千分位加入,
 */
fun String?.toAmountFormatFor4Place(roundingMode: RoundingMode = RoundingMode.DOWN) =
    toAmountFormatForNPlace(roundingMode, "####")

fun String?.toAmountFormatFor8Place(roundingMode: RoundingMode = RoundingMode.DOWN) =
    toAmountFormatForNPlace(roundingMode, "########")

fun String?.toAmountFormatFor5Place(roundingMode: RoundingMode = RoundingMode.DOWN) =
    toAmountFormatForNPlace(roundingMode, "#####")

fun String?.toAmountFormatFor2Place(roundingMode: RoundingMode = RoundingMode.DOWN) =
    toAmountFormatForNPlace(roundingMode, "##")

// ",##0.#xN"
fun String?.toAmountFormatForNPlace(
    roundingMode: RoundingMode = RoundingMode.DOWN,
    digits: String
): String {
    if (this == null) return "0"
    if (isBlank() || this == "0") return "0"
    if (contains(",")) return this
    return DecimalFormat(if (this.contains(".")) ",##0.$digits" else ",##0").let {
        it.roundingMode = roundingMode
        it.format(this.toBigDecimal())
    }
}


/**
 * // 金額千分位加入, 沒有小數點
 */
fun String?.toAmountWithoutDot(): String {
    if (this.isNullOrBlank()) return "0"
    if (contains(",")) return this
    return DecimalFormat(",##0").format(parseDouble(this))
}

/**
 * // 金額千分位加入, 可有小數點
 */
fun String?.toAmount(): String {
    if (this.isNullOrBlank()) return ""
    if (contains(",")) return this

    val i = this.lastIndexOf(".")
    DecimalFormat(",##0").format(parseDouble(this.split(".")[0])).let {
        return if (i > 0) it + this.takeLast(length - i)
        else it
    }
}

/**
 * 維持至少三位數,不刪除尾數非0的數字,加入千分位,捨棄小數點後面超過兩位數字.
 */
fun String?.toOddsFormat(): String {
    return when {
        this == null || this.isBlank() -> ""
        !this.contains('.') -> {
            when (if (startsWith('-')) length - 1 else length) {
                1 -> "$this.00"
                2 -> "$this.0"
                else -> DecimalFormat("###,###").format(this.toInt())
            }
        }
        else -> {
            val splitArr = split(".")
            var decimal = ""
            if (splitArr[1].isNotEmpty()) {
                var findIndex = -1
                var maxIndex = if (splitArr[1].length > 1) 1 else 0
                for (i in (0..maxIndex).reversed()) {
                    if (splitArr[1][i] != '0') {
                        findIndex = i
                        break
                    }
                }

                if (findIndex != -1) {
                    decimal = splitArr[1].substring(0, findIndex + 1)
                }
            }

            var intLength = splitArr[0].length
            if (startsWith('-'))
                intLength -= 1

            val needDecimalCount = 3 - intLength
            if (needDecimalCount > decimal.length) {
                repeat(needDecimalCount - decimal.length) {
                    decimal = decimal.plus('0')
                }
            }

            when {
                splitArr[0].length < 4 -> splitArr[0]
                else -> DecimalFormat("###,###").format(splitArr[0].toInt())
            }.let {
                if (decimal.isBlank()) {
                    it
                } else {
                    it.plus(".$decimal")
                }
            }
        }
    }
}

/**
 *  去空白
 */
fun String.removeBlank(): String = this.replace(" ", "")

/**
 * 無特殊字
 */
fun String.noSpecialChar(): Boolean = this.matches(RegexString.NO_SPECIAL_CHARACTER.toRegex())

/**
 * 取卡號後四碼
 */
fun String.getTailNumber(): String = this.takeLast(4)

fun String.convertDashTimeByFormat(
    toFormat: String = DateTime.YYYYMMDDHHMM_DASH,
    fromFormat: String = DateTime.YYYYMMDDHHMM_DASH,
): String? =
    (if (this.startsWith("20") && !this.startsWith("20-")) this else "20${this}").let {
        DateTime.getDateStrByFormat(fromFormat, toFormat, it)
    }

fun String.convertDashTimeByFormatSeconds(
    toFormat: String = DateTime.YYYYMMDDHHMMSS_DASH,
    fromFormat: String = DateTime.YYYYMMDDHHMMSS_DASH,
): String? =
    (if (this.startsWith("20") && !this.startsWith("20-")) this else "20${this}").let {
        DateTime.getDateStrByFormat(fromFormat, toFormat, it)
    }

/**
 * 含有+-號字串的反向 例: +1.5 -> -1.5
 */
fun String.reverse(): String = when {
    contains("+") -> this.replace("+", "-")
    contains("-") -> this.replace("-", "+")
    else -> this
}

/**
 * 賠率是否為0
 */
fun String.isZero(): Boolean = this == "0" || this == "0.0" || this == "0.00"

fun String.toSafeLong(): Long = try {
    if (this.isBlank()) 0 else
        DecimalFormat().parse(this)?.toLong() ?: 0
} catch (e: Exception) {
    Log.e("toSafeLong", "e = $e")
    0
}

fun String?.toSafeDouble(): Double = try {
    if (this.isNullOrBlank())
        0.0
    else {
        BigDecimal(this).toDouble() ?: 0.0
    }
} catch (e: Exception) {
    Log.e("toSafeDouble", "e = $e")
    0.0
}


fun String.toSafeInt(): Int = try {
    if (this.isBlank()) 0 else
        DecimalFormat().parse(this)?.toInt() ?: 0
} catch (e: Exception) {
    Log.e("toSafeInt", "e = $e")
    0
}

fun String.toSafeIndexInt(): Int = try {
    if (this.isBlank()) -1 else
        DecimalFormat().parse(this)?.toInt() ?: -1
} catch (e: Exception) {
    Log.e("toSafeIndexInt", "e = $e")
    -1
}


fun String.toSafeBigDecimal(): BigDecimal = try {
    if (this.isBlank()) BigDecimal.ZERO else this.toBigDecimal()
} catch (e: Exception) {
    Log.e("toSafeBigDecimal", "e = $e")
    BigDecimal.ZERO
}

fun String.copy(context: Context) {
    (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
        ClipData.newPlainText(
            "copy",
            this
        )
    )
}

fun String.toQRCodeBitmap(qrCodeWidth: Int, qrCodeHeight: Int): Bitmap {
    val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java).apply {
        put(EncodeHintType.CHARACTER_SET, "UTF-8")
        put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
    }
    val bitMatrix =
        MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hints)
    var firstBlockX = 0
    loop1@ for (y in 0 until qrCodeHeight) {
        loop2@ for (x in 0 until qrCodeWidth) {
            if (bitMatrix.get(x, y)) {
                firstBlockX = x
                break@loop1
            }
        }
    }

    val noEdgeWidthOfMatrix = qrCodeWidth - firstBlockX
    val noEdgeHeightOfMatrix = qrCodeHeight - firstBlockX
    val bitmap =
        Bitmap.createBitmap(
            noEdgeWidthOfMatrix - firstBlockX,
            noEdgeHeightOfMatrix - firstBlockX,
            Bitmap.Config.ARGB_8888
        )
    for (y in firstBlockX until noEdgeWidthOfMatrix) {
        for (x in firstBlockX until noEdgeHeightOfMatrix) {
            bitmap.setPixel(
                x - firstBlockX,
                y - firstBlockX,
                if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            )
        }
    }

    return bitmap
}

fun String.toIntegerString(): String = if (contains(".")) {
    this.split(".")[0]
} else this

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
fun String?.toCompactHtml() = this?.let {
    Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
}

fun String.replaceName(home: String, away: String, k: String): String {
    val NAME_HOME = "%home%"
    val NAME_AWAY = "%away%"
    val NAME_K = "%k%"
    var name = this
    name = name.replace(NAME_HOME, home)
    name = name.replace(NAME_AWAY, away)
    name = name.replace(NAME_K, k)
    return name
}

fun String?.emptyToNull(): String? = if (TextUtils.isEmpty(this)) null else this

fun String?.isNotNullOrEmpty() = !TextUtils.isEmpty(this)

fun String?.isNotNullOrBlank() = !TextUtils.isEmpty(this) && this?.isNotBlank() == true

fun SpannableStringBuilder.splitColor(delimiter: String, scoreColor: Int) {
    setSpan(
        ForegroundColorSpan(scoreColor),
        lastIndexOf(delimiter),
        length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun SpannableStringBuilder.scoreColorWithoutBrackets(
    homeScore: String,
    awayScore: String,
    scoreColor: Int
) {
    var start = length
    append(homeScore)
    setSpan(ForegroundColorSpan(scoreColor), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    append(" - ")
    start = length
    append(awayScore)
    setSpan(ForegroundColorSpan(scoreColor), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun SpannableStringBuilder.cornerScoreStyle(homeScore: String, awayScore: String, scoreColor: Int) {
    append(" ")
    val start = length
    append(homeScore)
    append("-")
    append(awayScore)
    setSpan(ForegroundColorSpan(scoreColor), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun SpannableStringBuilder.appendWithColor(string: String, scoreColor: Int) {
    val start = length
    append(string)
    setSpan(ForegroundColorSpan(scoreColor), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun String.toLowerCaseAndCapitalize()
        : String = this.toLowerCase(Locale.getDefault()).capitalize()

fun String.parseDate(format: SimpleDateFormat) = try {
    format.parse(this)
} catch (t: Throwable) {
    Timber.e(t, "[parseDate]: $this failed with format ${format.toPattern()}")
    null
}

fun String.parseDate(from: SimpleDateFormat, to: SimpleDateFormat) = try {
    this.parseDate(from)?.let { to.format(it) }
} catch (t: Throwable) {
    Timber.e(t, "[parseDate]: $this failed with from ${from.toPattern()} to ${to.toPattern()}")
    null
}

fun String.parseDateYMDHMSToYMD() =
    parseDate(DateTime.FORMAT_YYYYMMDDHHMMSS_DASH, DateTime.FORMAT_YYYYMMDD_DASH)

fun String.parseDateYMDHMSToYMDHM() =
    parseDate(DateTime.FORMAT_YYYYMMDDHHMMSS_DASH, DateTime.FORMAT_YYYYMMDDHHMM_DASH)

fun String.parseYearDateTimeSecondToYearDate() =
    parseDate(DateTime.FORMAT_YYYYMMDDHHMMSS_DASH, DateTime.FORMAT_YYYYMMDDHHMM_DASH)

fun String.parseYearDateTimeSecondToYearDateTimeSecond() =
    parseDate(DateTime.FORMAT_YYYYMMDDHHMMSS_DASH, DateTime.FORMAT_YYYYMMDDHHMMSS_DASH)

// by languages
fun String.parseYearDateTimeSecondToAppYearDateTimeDash() =
    parseDate(DateTime.FORMAT_YYYYMMDDHHMMSS_DASH, DateTime.FORMAT_YEAR_DATE_TIME_DASH)

fun String.toTimeInMillis(
    format: String = DateTime.YYYYMMDDHHMMSS_DASH,
    timeZone: TimeZone = TimeZone.getDefault()
): Long? =
    DateTime.getDateByFormat(format, this, timeZone)?.time

fun String.toHiddenName() = take(1) + "**"
fun String.toHiddenBankCardNo() = "**** **** **** " + takeLast(4)
fun toHiddenAmount() = "***"

fun String.subStringSet(len: Int): Set<String> {
    val strSet = HashSet<String>()

    var i = 0
    while ((i + len - 1) < length) {
        strSet.add(substring(i, i + len))
        i++
    }
    return strSet
}

// 8:3 -> 08:03
fun String.toFormattedTime() = this.let {
    val array = it.split(":")
    if (array.size != 2) {
        return this // return original value
    }

    val hour = if (array[0].length == 1) "0${array[0]}" else array[0]
    val minute = if (array[1].length == 1) "0${array[1]}" else array[1]

    "$hour:$minute"
}

fun String.matchScoreDashToScoreString() =
    this.matches(RegexString.SCORES_ONLY.toRegex())?.takeIf { it }?.let {
        this.replace("-", " vs ")
    } ?: ""

fun String.matchScoreDashToBaseBallScoreString() =
    this.matches(RegexString.SCORES_ONLY.toRegex())?.takeIf { it }?.let {
        val score = this.split("-")
        score[1] + " vs " + score[0]
    } ?: ""

fun String.subZeroAfterDot(): String = DecimalFormat("#.##").format(parseDouble(this))

fun String.subDot() = if (this.endsWith(".")) this.replace(".", "") else this