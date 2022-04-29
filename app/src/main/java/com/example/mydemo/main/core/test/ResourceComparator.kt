package com.example.mydemo.main.core.test

import android.content.Context
import androidx.annotation.StringRes

class ResourceComparator {

    fun isEqual(context: Context, @StringRes resId: Int, string: String): Boolean {

        return context.getString(resId) == string
    }

}