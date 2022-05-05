package com.example.mydemo.main.core.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mydemo.databinding.ActivityFlowBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FlowActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFlowBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            test1()
        }

        binding.btn2.setOnClickListener {
            test2()
        }

        binding.btn3.setOnClickListener {
            test3()
        }

        binding.btn4.setOnClickListener {
            Timber.e("start click")
            lifecycleScope.launch {
                Timber.e("start")
                val result = test4(true)
                Timber.e("get $result")
                Timber.e("end")
            }
            Timber.e("end click")
        }

    }

    private suspend fun test4(isTure: Boolean): Int {
        return suspendCoroutine { continuation ->
            if (isTure) {
                Thread.sleep(3000L)
                continuation.resume(1)
            } else {
                continuation.resume(2)
            }
        }
    }

    private fun test1() {
        lifecycleScope.launch {
            countDown()
                .filter { it % 2 == 0 }
                .map { it * it }
                .onEach { Timber.e("just do something in here ...") }
                //if the latest data had sent and the function hasn't finished,
                // then it will cancel the part that is working and start the new one
//                .collectLatest {
                .collect {
                    Timber.e("$it")
                }

            countDown()
                .transform {
                    emit("A${it}")
                    emit("B${it}")
                }.collect {
                    Timber.e("$it")
                }
        }
    }

    private fun test2() {
        lifecycleScope.launch {
            val result1 = countDown().count {
                it % 2 == 0
            }
            Timber.e("$result1")
            val result2 = countDown().reduce { accumulator, value ->
                accumulator + value
            }
            Timber.e("$result2")
            val result3 = countDown().fold(100) { accumulator, value ->
                accumulator + value
            }
            Timber.e("$result3")
        }
    }


    fun currTime() = System.currentTimeMillis()
    var start: Long = 0
    private fun test3() {
        lifecycleScope.launch {
            (1..5).asFlow()
                .onStart { start = currTime() }
                .onEach { delay(100) }
                .flatMapConcat { // it will wait for the concat scope to finish
                    flow {
                        emit("$it: First")
                        delay(500)
                        emit("$it: Second")
                    }
                }
                .collect {
                    Timber.e("$it at ${System.currentTimeMillis() - start} ms from start")
                }

            Timber.e("-----------------------------")

            (1..5).asFlow()
                .onStart { start = currTime() }
                .onEach { delay(100) }
                .flatMapMerge {// send the value every time when it's executed
                    flow {
                        emit("$it: First")
                        delay(500)
                        emit("$it: Second")
                    }
                }
                .collect {
                    Timber.e("$it at ${System.currentTimeMillis() - start} ms from start")
                }
        }
    }

    private suspend fun countDown() = flow {
        var count = 10
        while (count >= 0) {
            delay(300L)
            emit(count)
            count--
        }
    }
}