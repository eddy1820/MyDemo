package com.example.mydemo.main.core.coroutine.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.mydemo.R
import com.example.mydemo.databinding.ViewCustomizedBinding

class CustomizedView constructor(context: Context, attributeSet: AttributeSet) :
    RelativeLayout(context, attributeSet) {

    private val binding = ViewCustomizedBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomizedView, 0, 0).apply {
            val title = getString(R.styleable.CustomizedView_title)
            binding.titleLabel.text = title
            recycle()
        }
    }

    fun initView() {

    }


    fun setText(text: String) {
        binding.titleLabel.text = text
    }
}