package com.example.mydemo.base

import android.app.Application
import com.example.mydemo.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(e: StackTraceElement): String {
//                    return super.createStackElementTag(e)
//                    return "[${super.createStackElementTag(e)}][${e.methodName}]"
                    return "(${e.fileName}:${e.lineNumber})#${e.methodName}|${Thread.currentThread().name}"
                }
            })
        }
    }

}