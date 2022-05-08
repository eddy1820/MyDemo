package com.example.mydemo.main.core.worker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.mydemo.databinding.ActivityWorkerBinding
import timber.log.Timber
import java.util.concurrent.TimeUnit

class WorkerActivity : AppCompatActivity() {
    companion object {
        fun start(activity: Activity) {
            Intent(activity, WorkerActivity::class.java).apply {
            }.let(activity::startActivity)
        }
    }

    private lateinit var binding: ActivityWorkerBinding
    private val workerManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val workerRequestA = createWork("work A")
            val workerRequestB = createWork("work B")
//      workerManager.enqueue(workerRequest)

            val saveRequest =
                PeriodicWorkRequestBuilder<MyWorker>(2000, TimeUnit.MILLISECONDS)
                    .setInitialDelay(1000, TimeUnit.MILLISECONDS)
                    .setInputData(workDataOf(INPUT_DATA_KEY to "work A"))
                    .build()

            workerManager.enqueue(saveRequest)


//      workerManager.beginWith(workerRequestA)
//        .then(workerRequestB)
//        .enqueue()

            workerManager.getWorkInfoByIdLiveData(saveRequest.id).observe(this, {
                Timber.e("observe: ${it.state}")

                if (it.state == WorkInfo.State.SUCCEEDED) {
                    val out = it.outputData.getString(OUT_DATA_KEY)
                    Timber.e("onCreate: $out")
                }

            })
        }

    }

    private fun createWork(name: String): OneTimeWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf(INPUT_DATA_KEY to name))
            .build()
    }
}