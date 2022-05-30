package com.example.mydemo.main.core.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.mydemo.databinding.ViewEdBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class EDView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding = ViewEdBinding.inflate(LayoutInflater.from(context), this, false)

    private val _textChangedCallback = MutableSharedFlow<String>()
    val textChangedCallback = _textChangedCallback.asSharedFlow()
    private val canInput = AtomicBoolean(true)

    private val TEXT = 1
    private val PASSWORD = 2
    private val NUMBER = 3

    init {
        addView(binding.root)
        initCallback()
    }

    fun canInput(canInput: Boolean) {
        this.canInput.set(canInput)
        if (this.canInput.get()) {
            binding.edittextLayout.alpha = 1f
            binding.edit.isEnabled = true
        } else {
            binding.edittextLayout.alpha = 0.4f
            binding.edit.isEnabled = false
            binding.edit.clearFocus()
        }
    }

    fun canInt() = canInput.get()

    fun getText() = binding.edit.text.toString()

    fun setText(string: String) {
        binding.edit.setText(string)
    }

    var name = ""
    private fun initCallback() {
//        binding.inputEdit.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
//                    Timber.e("${name} : ${s.toString()}")
//                    _textChangedCallback.emit(s.toString())
//
//                }
//            }
//        })


    }


}