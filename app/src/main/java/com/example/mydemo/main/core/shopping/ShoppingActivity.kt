package com.example.mydemo.main.core.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.mydemo.BuildConfig
import com.example.mydemo.databinding.ActivityMainBinding
import com.example.mydemo.databinding.ActivityShoppingBinding
import com.example.mydemo.main.core.shopping.ui.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ShoppingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        BuildConfig.API_KEY
    }
}