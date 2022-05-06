package com.example.mydemo.main.core.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mydemo.R
import com.example.mydemo.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val result = RegistrationUtil.validateRegistrationInput(
                binding.accountEdit.text.toString(),
                binding.passwordEdit.text.toString(),
                binding.confirmPasswordEdit.text.toString()
            )

            if (result) {
                startActivity(Intent(this, Test2Activity::class.java))
            } else {
                Toast.makeText(this, getString(R.string.wrong_information), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}