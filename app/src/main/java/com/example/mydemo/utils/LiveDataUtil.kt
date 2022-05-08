package com.example.mydemo.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData


object LiveDataUtils {
    private var sMainHandler: Handler? = null

    /**
     * 用 setValue 更新 MutableLiveData 的數據，如果在子線程，就切換到主線程
     */
    fun <T> setValue(mld: MutableLiveData<T>?, d: T) {
        if (mld == null) {
            return
        }
        if (Thread.currentThread() === Looper.getMainLooper().thread) {
            mld.setValue(d)
        } else {
            postSetValue(mld, d)
        }
    }

    /**
     * 向主線程的 handler 拋 SetValueRunnable
     * postValue 數據丟失的問題在我的前一篇文章中也提到過，
     * 大家也可以直接看源碼。postValue 只是把傳進來的數據先存到 mPendingData，然後往主線程拋一個 Runnable，
     * 在這個 Runnable 裡面再調用 setValue 來把存起來的值真正設置上去，並回調觀察者們。
     * 而如果在這個 Runnable 執行前多次 postValue，其實只是改變暫存的值 mPendingData，並不會再次拋另一個 Runnable。
     * 這就會出現後設置的值把前面的值覆蓋掉的問題，會導致事件丟失。
     * https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/672834/#outline__3_1
     */
    fun <T> postSetValue(mld: MutableLiveData<T>, d: T) {
        if (sMainHandler == null) {
            sMainHandler = Handler(Looper.getMainLooper())
        }
        sMainHandler?.post(SetValueRunnable.create(mld, d))
    }

    private class SetValueRunnable<T> private constructor(
        @param:NonNull private val liveData: MutableLiveData<T>,
        private val data: T
    ) : Runnable {

        companion object {
            fun <T> create(@NonNull liveData: MutableLiveData<T>, data: T): SetValueRunnable<T> {
                return SetValueRunnable(liveData, data)
            }
        }

        override fun run() {
            liveData!!.value = data
        }
    }
}