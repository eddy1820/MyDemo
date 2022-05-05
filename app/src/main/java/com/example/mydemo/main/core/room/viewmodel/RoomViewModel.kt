package com.example.mydemo.main.core.room.viewmodel

import androidx.lifecycle.*
import com.example.mydemo.model.CurrencyInfo
import com.example.mydemo.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _onGetCurrencyInfo: MutableLiveData<List<CurrencyInfo>> by lazy { MutableLiveData<List<CurrencyInfo>>() }
    val onGetCurrencyInfo: LiveData<List<CurrencyInfo>> = _onGetCurrencyInfo

    fun getCurrencyData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCurrency()
                .catch { Timber.e("getCurrencyData: $it") }
                .collect { _onGetCurrencyInfo.postValue(it) }
        }
    }
}