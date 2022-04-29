package com.example.mydemo.main.core.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydemo.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}