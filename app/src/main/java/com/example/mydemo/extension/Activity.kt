package com.example.mydemo.extension

import android.app.Activity
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.view.View

fun Activity.isExistActivity(activity: Class<*>): Boolean {
    val intent = Intent(this, activity)
    val cmpName = intent.resolveActivity(packageManager)
    var flag = false
    if (cmpName != null) {// 说明系统中存在这个activity
        val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        val taskInfoList = am!!.getRunningTasks(10)//获取从栈顶开始往下查找的10个activity
        for (taskInfo in taskInfoList) {
            // todo 版本警告  目前低版本也可運行，先忽略
            if (taskInfo.baseActivity == cmpName) {// 说明它已经启动了
                flag = true
                break//跳出循环，优化效率
            }
        }
    }
    return flag//true 存在 falese 不存在
}

fun Activity.isInstalledApp(pkgName: String): Boolean {
    return try {
        packageManager.getPackageInfo(pkgName, 0)
        true
    } catch (e: Exception) {
        false
    }
}

fun Activity.hideKeyboard() {
//  val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//  val windowToken = findViewById<View>(android.R.id.content).rootView.windowToken
//  imm.hideSoftInputFromWindow(windowToken, 0)
    hideKeyboard(findViewById<View>(android.R.id.content).rootView)
}

// https://stackoverflow.com/a/37380780
fun Activity.hideAllSystemUI() {
    val uiOptions = window.decorView.systemUiVisibility
    var newUiOptions = uiOptions
    if (Build.VERSION.SDK_INT >= 14) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    if (Build.VERSION.SDK_INT >= 16) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 18) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
    window.decorView.systemUiVisibility = newUiOptions
}

fun Activity.showAllSystemUI() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
}

fun Activity.getScreenWidth(): Int {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return metric.widthPixels
}

fun Activity.getScreenHeight(): Int {
    val metric = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metric)
    return metric.heightPixels
}

fun Activity.killApp() {
    finishAffinity()
    android.os.Process.killProcess(android.os.Process.myPid())
}

fun Activity.activityTransitionFadeInOut() {
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}