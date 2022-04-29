package com.example.mydemo.main.core.coroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mydemo.databinding.ActivityCoroutineBinding
import com.example.mydemo.main.core.coroutine.viewmodel.CoroutineViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    }
}