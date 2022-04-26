package com.example.mydemo.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
  companion object {
    const val TAG = "BaseViewModel"
  }

  protected val compositeDisposable = CompositeDisposable()

  fun clearCompositeDisposable() {
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
  }
}