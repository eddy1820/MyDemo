package com.example.mydemo.extension

import android.content.res.Resources
import java.math.BigDecimal

fun Double.isZero(): Boolean = this == 0.0

fun Double.isNonZero(): Boolean = this != 0.0


fun Double.toBTC(): BigDecimal = (this / 1000000).toBigDecimal()

val Double.toDp: Double
    get() = (this / Resources.getSystem().displayMetrics.density)

val Double.toPx: Double
    get() = (this * Resources.getSystem().displayMetrics.density)