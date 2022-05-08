package com.example.mydemo.extension

import java.math.BigDecimal
import java.math.RoundingMode


fun BigDecimal.toAmountFormatFor2Place(roundingMode: RoundingMode = RoundingMode.DOWN) =
    this.toString().toAmountFormatFor2Place(roundingMode)

fun BigDecimal.isNonZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0

fun BigDecimal.isLessThanZero() = this < BigDecimal.ZERO

fun BigDecimal.isGreaterThanZero() = this > BigDecimal.ZERO

fun BigDecimal.toOddsFormat(): String = this.toString().toOddsFormat()


private fun BigDecimal.convertToEUOdds(): BigDecimal = this + BigDecimal.ONE

private fun BigDecimal.convertSignedOddsToEUOdds(): BigDecimal =
    ((1.00 / this.toDouble()).toBigDecimal().abs().setScale(2, RoundingMode.DOWN)) + BigDecimal.ONE

private fun BigDecimal.convertMultiTypeOddsToOdds(): BigDecimal =
    if (this.isGreaterThanZero()) this.convertToEUOdds()
    else this.convertSignedOddsToEUOdds()

private fun BigDecimal.convertOddsFromHKToEU(): BigDecimal = this.convertToEUOdds()

private fun BigDecimal.convertOddsFormHKToML(): BigDecimal {
    val euOdds = this.convertOddsFromHKToEU()
    return if (euOdds <= BigDecimal(2)) euOdds - BigDecimal.ONE
    else -((1.00 / (euOdds - BigDecimal.ONE).toDouble()).toBigDecimal()
        .setScale(2, RoundingMode.DOWN)) // 8/17 Thea 說賠率轉換後無條件捨去第二位後
}

private fun BigDecimal.convertOddsFromHKToIN(): BigDecimal {
    val euOdds = this.convertOddsFromHKToEU()
    return if (euOdds > BigDecimal(2)) euOdds - BigDecimal.ONE
    else -((1.00 / (euOdds - BigDecimal.ONE).toDouble()).toBigDecimal()
        .setScale(2, RoundingMode.DOWN))   // 8/17 Thea 說賠率轉換後無條件捨去第二位後
}

fun BigDecimal.toUBTC() = (this * BigDecimal(1000000))

fun BigDecimal.toMBTC() = (this * BigDecimal(1000))

fun BigDecimal.floorToHundred() = (this.toDouble() / 100.00).toBigDecimal()
    .setScale(0, RoundingMode.DOWN) * BigDecimal(100).setScale(2)

fun BigDecimal.subZeros(): String = this.stripTrailingZeros().toPlainString()

fun BigDecimal.subDecimal(): BigDecimal = this.setScale(0, RoundingMode.DOWN)