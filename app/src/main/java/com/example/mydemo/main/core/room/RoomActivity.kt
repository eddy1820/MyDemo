package com.example.mydemo.main.core.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mydemo.databinding.ActivityRoomBinding
import com.example.mydemo.main.core.room.adapter.CurrencyAdapter
import com.example.mydemo.main.core.room.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomActivity : AppCompatActivity() {

    private val viewModel by viewModels<RoomViewModel>()
    private val binding by lazy { ActivityRoomBinding.inflate(layoutInflater) }
    private var adapter: CurrencyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initLiveData()
    }

    private fun initLiveData() {
        viewModel.onGetCurrencyInfo.observe(this, {
            adapter?.submitList(it)
        })
    }

    private fun initView() {
        viewModel.getCurrencyData()
        adapter = CurrencyAdapter(onItemClick = {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        })
        binding.recyclerView.adapter = adapter
    }
}