package com.example.mydemo.base

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
  protected val compositeDisposable = CompositeDisposable()

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }
}