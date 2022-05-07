package com.example.mydemo.main.core.test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemo.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {

    private val binding by lazy { ActivityTest2Binding.inflate(layoutInflater) }

    companion object {
        fun start(activity: Activity) {
            Intent(activity, Test2Activity::class.java).apply {
                Bundle().apply {
                }.let(this::putExtras)
            }.let(activity::startActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonTv.setOnClickListener {
            finish()
        }
    }
}