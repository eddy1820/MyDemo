package com.example.mydemo.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.databinding.ActivityMainBinding
import com.example.mydemo.main.core.api.view.VectorActivity
import com.example.mydemo.main.core.compose.ComposeActivity
import com.example.mydemo.main.core.coroutine.view.CoroutineActivity
import com.example.mydemo.main.core.flow.FlowActivity
import com.example.mydemo.main.core.navigation.NavigationActivity
import com.example.mydemo.main.core.room.RoomActivity
import com.example.mydemo.main.core.shopping.ShoppingActivity
import com.example.mydemo.main.core.test.TestActivity
import com.example.mydemo.main.core.worker.WorkerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val list = listOf(
        PageItem("Room", RoomActivity::class.java),
        PageItem("API", VectorActivity::class.java),
        PageItem("Navigation", NavigationActivity::class.java),
        PageItem("Coroutine", CoroutineActivity::class.java),
        PageItem("Flow", FlowActivity::class.java),
        PageItem("Test", TestActivity::class.java),
        PageItem("Shopping", ShoppingActivity::class.java),
        PageItem("WorkManager", WorkerActivity::class.java),
        PageItem("Compose", ComposeActivity::class.java),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = MainAdapter {
            startActivity(Intent(this, it.activityClass))
        }.apply { submitList(list) }
    }
}

data class PageItem(val title: String, val activityClass: Class<out Activity>)