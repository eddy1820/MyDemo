package com.example.mydemo.main.core.shopping.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mydemo.R
import com.example.mydemo.main.core.coroutine.viewmodel.CoroutineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

//    lateinit var viewModel: ShoppingViewModel
    private val viewModel by activityViewModels<ShoppingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }

    fun getX() = viewModel.getXXX()

}