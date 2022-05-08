package com.example.mydemo.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.example.mydemo.extension.hide
import com.example.mydemo.extension.inVisible
import com.example.mydemo.extension.show
import com.example.mydemo.extension.showIf
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.*

object CommonUtil {

    const val TAG = "CommonUtil"
    const val UNKNOWN = "Unknown"
    fun getScreenResolution(context: Context): String = Point().let {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)
            .defaultDisplay.getRealSize(it)
        val s = it.x.toString() + "x" + it.y.toString()
//        Log.d(TAG, "screenResolution = $s")
        return s
    }

    fun getOsVersion(): String = Build.VERSION.RELEASE ?: UNKNOWN

    fun getBrand(): String {
//        Log.d(TAG, "Build.BRAND = " + Build.BRAND)
//        Log.d(TAG, "Build.MANUFACTURER = " + Build.MANUFACTURER)
//        Log.d(TAG, "Build.MODEL = " + Build.MODEL)
//        Log.d(TAG, "Build.PRODUCT = " + Build.PRODUCT)
        return (Build.BRAND ?: Build.MANUFACTURER ?: UNKNOWN)
    }

    fun getModel(): String = Build.MODEL ?: UNKNOWN
    fun showKeyboard(activity: Activity, view: EditText, isResume: Boolean = false) {
        view.postDelayed(if (isResume) 333L else 50L) {
            view.requestFocus()
            (activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view, 0)
        }
    }

    @Suppress("DEPRECATION")
    fun saveFileToMedia(
        context: Context,
        contentResolver: ContentResolver,
        bitmap: Bitmap
    ): Boolean {
        if (Build.VERSION.SDK_INT >= 29) return saveBitmap(contentResolver, bitmap) else
            saveBitmapToLocal(bitmap, context)?.let {
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    it.absolutePath,
                    it.name,
                    it.name
                )
                return it.exists()
            }
        return false
    }

    @Suppress("UNREACHABLE_CODE")
    @RequiresApi(29)
    @Throws(IOException::class)
    private fun saveBitmap(
        contentResolver: ContentResolver, @NonNull bitmap: Bitmap
    ): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}_qrcode.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        var stream: OutputStream? = null
        var uri: Uri? = null
        try {
            val contentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = contentResolver.insert(contentUri, contentValues)
            if (uri == null) throw IOException("Failed to create new MediaStore record.")
            stream = contentResolver.openOutputStream(uri)
            if (stream == null) throw IOException("Failed to get output stream.")
            if (!bitmap.compress(
                    CompressFormat.JPEG,
                    95,
                    stream
                )
            ) throw IOException("Failed to save bitmap.")
        } catch (e: IOException) {
            if (uri != null) { // Don't leave an orphan entry in the MediaStore
                contentResolver.delete(uri, null, null)
            }
            throw e
        } finally {
            stream?.close()
            return uri != null
        }
    }

    fun saveBitmapToLocal(bitmap: Bitmap, activity: Activity): Observable<Boolean> =
        Observable.fromCallable {
            saveFileToMedia(activity, activity.contentResolver, bitmap)
        }
            .subscribeOn(Schedulers.io())

    fun saveBitmapToLocal(bitmap: Bitmap, context: Context): File? = try {
        val path =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path + File.separator
        File(path).mkdirs()
        val file = File("$path/", "tmp_qrcode.png")
        file.delete()
        val fOutputStream = FileOutputStream(file)
        bitmap.compress(CompressFormat.PNG, 100, fOutputStream)
        fOutputStream.flush()
        fOutputStream.close()
        file
    } catch (e: Exception) {
//        Log.e("saveQRcodeImgToLocal", "e = $e")
        null
    }

    fun writeToFile(data: String, fileName: String, context: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(context.openFileOutput("fileName.txt", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
//            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    // const val is6789Flavor = BuildConfig.FLAVOR == BuildConfig.FLAVOR_BET_6789_NAME
//  const val is978Flavor = BuildConfig.FLAVOR == BuildConfig.FLAVOR_BET_978_NAME
}

inline fun <T> objectsToDo(vararg obj: T?, crossinline handler: (obj: T) -> Unit) =
    obj.distinct().forEach {
        it?.let {
            handler.invoke(it)
        }
    }

inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
}

inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
}

inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    obj4: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
    handler.invoke(obj4)
}

inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    obj4: T,
    obj5: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
    handler.invoke(obj4)
    handler.invoke(obj5)
}


inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    obj4: T,
    obj5: T,
    obj6: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
    handler.invoke(obj4)
    handler.invoke(obj5)
    handler.invoke(obj6)
}


inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    obj4: T,
    obj5: T,
    obj6: T,
    obj7: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
    handler.invoke(obj4)
    handler.invoke(obj5)
    handler.invoke(obj6)
    handler.invoke(obj7)
}

inline fun <T> objectsToDo(
    obj1: T,
    obj2: T,
    obj3: T,
    obj4: T,
    obj5: T,
    obj6: T,
    obj7: T,
    obj8: T,
    crossinline handler: (obj: T) -> Unit
) {
    handler.invoke(obj1)
    handler.invoke(obj2)
    handler.invoke(obj3)
    handler.invoke(obj4)
    handler.invoke(obj5)
    handler.invoke(obj6)
    handler.invoke(obj7)
    handler.invoke(obj8)
}


fun viewsToHide(vararg view: View?) = view.forEach { it?.hide() }
fun viewsToShow(vararg view: View?) = view.forEach { it?.show() }
fun viewsToInVisible(vararg view: View?) = view.forEach { it?.inVisible() }
fun viewsToEnableIf(boolean: Boolean, vararg view: View?) = view.forEach { it?.isEnabled = boolean }
fun viewsToShowIf(boolean: Boolean, vararg view: View?) = view.forEach {
    it?.showIf(boolean)
}

// UI Rendering is the act of generating a frame from your app and displaying it on the screen. To ensure that a user's interaction with your app is smooth, your app should render frames in under 16ms to achieve 60 frames per second (why 60fps?). If your app suffers from slow UI rendering, then the system is forced to skip frames and the user will perceive stuttering in your app. We call this jank.
// https://developer.android.com/topic/performance/vitals/render
// bad performance occur at 10~14ms in some device
inline fun isPerformanceGood(job: () -> Unit): Boolean {
    val startTimeMillis = System.currentTimeMillis()
    job.invoke()
    val finishedTimeMillis = System.currentTimeMillis()

    val b = finishedTimeMillis - startTimeMillis < 10L
//  Log.d(TAG, "isGoodPerformance() startTimeMillis: $startTimeMillis")
//  Log.d(TAG, "isGoodPerformance() finishedTimeMillis: $finishedTimeMillis")
//  Log.d(TAG, "isGoodPerformance() returned: $b")
    return b
}
