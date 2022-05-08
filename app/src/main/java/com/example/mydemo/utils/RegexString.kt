package com.example.mydemo.utils

object RegexString {
    const val WHITESPACE_CHARACTER = "^.*[\\s]+.*$" // 含空白
    const val ACCOUNT = "^[A-Za-z0-9]{6,15}\$"
    const val CURRENCY_ADDRESS = "^[A-Za-z0-9]{1,200}\$"
    const val PASSWORD = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,15}\$"
    const val SPECIAL_CHARACTER = "^.*[^\\w\\s\\u4E00-\\u9FA5].*$" // 含特殊字元(不是英文、不是空白、不是漢字)
    const val NO_SPECIAL_CHARACTER = "[\\u4E00-\\u9FA5a-zA-Z1234567890]+" // 不含特殊字元(含英文、空白、漢字)
    const val CONTAINS_NUMBER = "^.*(?=.*[0-9]).*$"
    const val ALPHABET = "^[A-Za-z]*$"
    const val REAL_NAME = "^[A-Za-z\\u4E00-\\u9FA5\\.·\\s]*$"
    const val ALPHABET_AND_NUMERIC = "^[A-Za-z0-9]*$"
    const val ALPHABET_AND_NUMERIC_ONE = "^.*(?=.*[0-9])(?=.*[a-zA-Z]).*$"
    const val LENGTH_8_20 = "^.{8,20}$"
    const val NUMERIC_11 = "^[0-9]{11}$"
    const val NUMERIC_6_10 = "^[0-9]{6,10}$"
    const val ACCOUNT_2 = "^[A-Za-z0-9]{5,13}$"
    const val CAPTCHA_4 = "^[0-9]{4}$"
    const val EMAIL =
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    const val SECURITY_LEVEL_2 = "^.*[A-Z].*$"     //最少须包含一个大写字母
    const val SECURITY_LEVEL_3 =
        "^.*[^\\w\\s].*$" //最少须包含一个特殊字符 ( ] [ ? / < ~ # ` ! @ $ % ^ & * ( ) + = } | : " ; ' , > { )
    const val SECURITY_LEVEL_4_SAME = "^.*(\\d)\\1{2}.*$" //連續3個相同數字
    const val CHINESE_2_10 = "[\\u4E00-\\u9FA5]{2,10}" //連續3個相同數字
    const val CHINESE_1_10 = "[\\u4E00-\\u9FA5]{1,10}" //連續3個相同數字
    const val SECURITY_LEVEL_4_CONTINUOUS =
        "^.*(012|123|234|345|456|567|678|789|987|876|765|654|543|432|321|210).*$" //連續3個升序或降序的數字
    const val NICKNAME = "^[\\u4E00-\\u9FA5_a-zA-Z0-9_]+$"
    const val NAME_CAN_START_WITH_SYMBOL = "^[\\u4E00-\\u9FA5.．·・‧]{1,20}"
    const val SCORES_ONLY = "^\\d*-\\d*\$"
    const val SCORES_WITH_CANCEL_REASON = "^\\d*-\\d*\\s{1}.{1,}\$"
    const val NUMBER = "^\\d{1,}.{0,}$"
    fun number(min: Int, max: Int): String = "^[0-9]{$min,$max}$"
    fun alphabetOrNumeric(min: Int, max: Int): String = "^[A-Za-z0-9]{$min,$max}$"


    // backend give the rules for us
    // cny = "^[\\u4e00-\\u9fa5|\\uff0e|\\u002e|\\u00B7|\\u2027|\\u30fb]{2,20}\$"
    // not_cny = "^((?!(\\s\\s+)|([0-9`~!@#\$%^&*()_|+\\-=?;:'\",<>\\{\\}\\[\\]\\\\\\/])).){2,50}\$" <- it can not prevent lots of punctuation such as emoji
    // const val NAME_CNY = "^[\\u4e00-\\u9fa5|\\uff0e|\\u002e|\\u00B7|\\u2027|\\u30fb]{2,20}\$"
    const val NAME_ELSE =
        "^((?!(\\s\\s+)|([0-9`~!@#\$%^&*()_|+\\-=?;:'\",<>\\{\\}\\[\\]\\\\\\/])).){2,50}\$"
    const val DEPOSIT_METHOD_NAME = "^[A-Za-z0-9\\u4e00-\\u9fa5]{1,50}\$"

    /** @see com.tiger.app.util.CurrencyUtil.Currency.isValidNameRule} prevent double space there */
}