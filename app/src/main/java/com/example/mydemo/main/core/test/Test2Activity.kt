package com.example.mydemo.main.core.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemo.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityTest2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTv.setOnClickListener {
            finish()
        }
    }
}