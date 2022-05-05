package com.example.mydemo.main.core.coroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mydemo.databinding.ActivityCoroutineBinding
import com.example.mydemo.main.core.coroutine.viewmodel.CoroutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class CoroutineActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCoroutineBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CoroutineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.customizedView.initView()

        binding.btn1.setOnClickListener {
            viewModel.getData1()
        }
        binding.btn2.setOnClickListener {
            viewModel.getData2()
        }
        binding.btn3.setOnClickListener {
            viewModel.getData3()
        }
        binding.btn4.setOnClickListener {
            viewModel.getData4()
        }
        binding.btn5.setOnClickListener {
            test1()
        }
        binding.btn6.setOnClickListener {
            test2()
        }
        binding.btn7.setOnClickListener {
            test3()
        }
        binding.btn8.setOnClickListener {
            test4()
        }
        binding.btn9.setOnClickListener {
            test5()
        }
        binding.btn10.setOnClickListener {
            test6()
        }

    }

    private fun test1() {
        Timber.e("1")
        lifecycleScope.launch(Dispatchers.IO) {
            val result = getText1()
            withContext(Dispatchers.Main) {
                binding.customizedView.setText(result)
                Timber.e("2")
            }
        }
        Timber.e("3")
    }

    private fun test2() {
        Timber.e("1")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(1000)
                Timber.e("A")
            }
            launch(Dispatchers.IO) {
                delay(1000)
                Timber.e("B")
            }
            delay(3000)
            Timber.e("2")
        }
        Timber.e("3")
    }

    private fun test3() {
        Timber.e("1")
        val job = lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            Timber.e("2")
        }
        Timber.e("3")
        runBlocking {
            Timber.e("4")
            job.join()
            Timber.e("5")
        }
    }


    /**
     * sometimes coroutine was so busy that it couldn't be canceled
     * we have to add an isActive condition to stop its calculation
     * or using the withTimeout function
     */
    private fun test4() {
        val job = lifecycleScope.launch(Dispatchers.IO) {
            Timber.e("starting long running calculation")
            for (i in 30..50) {
//                withTimeout(3000L) {
//
//                }
                if (isActive) {
                    Timber.e("fib is running for i = ${i}: ${fib(i)}")
                }
            }
            Timber.e("ending long running calculation")
            Timber.e("2")
        }
        runBlocking {
            delay(1000)
            job.cancel()
            Timber.e("cancel job")
        }
    }

    private fun test5() {
        lifecycleScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val text1 = async { getText1() }
                val text2 = async { getText2() }
                Timber.e("text1: ${text1.await()}, text2: ${text2.await()}")
            }
            Timber.e("Requests took: $time")
        }
    }

    private fun test6() {

    }

    private suspend fun getText1(): String {
        delay(1500L)
        return "Hello 1"
    }

    private suspend fun getText2(): String {
        delay(1500L)
        return "Hello 2"
    }

    private fun fib(n: Int): Int {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}