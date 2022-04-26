package com.example.mydemo.main.core.api.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mydemo.databinding.ActivityVectorBinding
import com.example.mydemo.main.core.api.viewmodel.VectorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VectorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityVectorBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<VectorViewModel>()
    private var adapter: VectorAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getVector()
        initView()
        initLiveData()

    }

    private fun initView() {
        adapter = VectorAdapter(onItemClick = {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        binding.recyclerView.adapter = adapter
    }

    private fun initLiveData() {
        viewModel.onGetVector.observe(this, {
            if (it.isSuccess()) {
                it.res?.let { adapter?.submitList(it) }
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}