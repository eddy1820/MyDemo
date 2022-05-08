package com.example.mydemo.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.isAppExist(uri: String): Boolean = try {
    packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}

@ColorInt
fun Context.getSafeColor(res: Int) = if (res != 0) ContextCompat.getColor(this, res) else null

fun Context.copyText(text: String) {
    (this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
        setPrimaryClip(ClipData.newPlainText(null, text))
    }
}

fun Context.getStatusBarHeight(): Int =
    applicationContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        ?.takeIf { it != 0 }?.let {
        applicationContext.resources.getDimensionPixelSize(it)
    } ?: 0

fun Context.getBottomNavigationBarHeight(): Int =
    applicationContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        ?.takeIf { it != 0 }?.let {
        applicationContext.resources.getDimensionPixelSize(it)
    } ?: 0

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT): Toast =
    toast(getString(stringRes), duration)

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this.applicationContext, text, duration).apply { show() }

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}