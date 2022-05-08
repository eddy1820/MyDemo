package com.tiger.app.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * Created by liq on 2017/7/6.
 */

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

inline val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.toDp: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

inline val Float.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun Int.r(): Int {
    return this shr 16 and 0xff
}

fun Int.g(): Int {
    return this shr 8 and 0xff
}

fun Int.b(): Int {
    return this and 0xff
}

fun Int.toBitmapDrawableWithDensity(context: Context, width: Int, height: Int): BitmapDrawable? =
    ContextCompat.getDrawable(context, this)?.let {
        var toBitmap = it.toBitmap(width, height)
        // Log.d(TAG, "density: ${resources.displayMetrics.density}")
        // Log.d(TAG, "densityDpi: ${resources.displayMetrics.densityDpi}")
        // Log.d(TAG, "toBitmap.density: ${toBitmap.density}")
        // Log.d(TAG, "toBitmap: ${toBitmap.height}")
        // Log.d(TAG, "toBitmap: ${toBitmap.getScaledHeight(resources.displayMetrics)}")

        if (context.resources.displayMetrics.densityDpi == toBitmap.density) {
            val newWidth = width.toPx
            val newHeight = height.toPx
            toBitmap = it.toBitmap(newWidth, newHeight)
        }
        BitmapDrawable(context.resources, toBitmap)
    }