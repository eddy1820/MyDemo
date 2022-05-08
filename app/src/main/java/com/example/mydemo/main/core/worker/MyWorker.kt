package com.example.mydemo.main.core.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import timber.log.Timber


const val INPUT_DATA_KEY = "INPUT_DATA_KEY"
const val OUT_DATA_KEY = "OUT_DATA_KEY"

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "MyWorker"

    }

    override fun doWork(): Result {

//    Log.e(TAG, "doWork: started")
        val name = inputData.getString(INPUT_DATA_KEY)
//    Thread.sleep(1000)
        Timber.e("doWork: ended $name")
        return Result.success(workDataOf(OUT_DATA_KEY to "work A output"))
    }
}