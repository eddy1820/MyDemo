package com.example.mydemo.main.core.coroutine.viewmodel

import androidx.lifecycle.*
import com.example.mydemo.base.BaseViewModel
import com.example.mydemo.dto.Resource
import com.example.mydemo.model.VectorResponse
import com.example.mydemo.repository.CurrencyRepository
import com.example.mydemo.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoroutineViewModel @Inject constructor(
    private val interViewRepository: InterviewRepository,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel() {
    companion object {
        private const val TYPE_DIVIDER = "divider"
        private const val TYPE_NEWS = "news"
    }

    private val _onGetVector: MutableLiveData<Resource<List<VectorResponse.Item>>> by lazy { MutableLiveData<Resource<List<VectorResponse.Item>>>() }
    val onGetVector: LiveData<Resource<List<VectorResponse.Item>>> = _onGetVector


    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
        Timber.e(exception.toString())
    }

    fun getData1() {
        viewModelScope.launch(Dispatchers.IO) {
            interViewRepository.getVectorWithFlow()
                .catch {
                    Timber.e("catch exception : $this")
                }
                .collect {
                    if (it.isSuccess()) {
                        it.res?.let {
                            Timber.e("is Success : $it")
                        }
                    } else {
                        Timber.e("is Fail : $it")
                    }
                }
        }
    }


    fun getData2() {
        viewModelScope.launch(Dispatchers.IO) {
            interViewRepository.getVectorWithFlow2()
                .catch {
                    Timber.e("catch exception : $this")
                }
                .collect {
                    if (it.isSuccess()) {
                        it.res?.let {
                            Timber.e("is Success : $it")
                        }

                    } else {
                        Timber.e("is Fail : $it")
                    }
                }
        }
    }

    fun getData3() {
        viewModelScope.launch(Dispatchers.IO) {
            interViewRepository.getVectorWithFlow3()
                .catch {
                    Timber.e("catch exception : $this")
                }
                .collect {
                    if (it.isSuccess()) {
                        it.res?.let {
                            Timber.e("is Success : $it")
                        }
                    } else {
                        Timber.e("is Fail : $it")
                    }
                }
        }
    }


    fun getData4() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                interViewRepository.getVectorWithFlow(),
                interViewRepository.getVectorWithFlow3()
            ) { data1, data2 ->

                Timber.e("data1 : $data1")
                Timber.e("data2 : $data2")

            }.catch {
                Timber.e("catch exception : $this")
            }.collect {

            }


        }
    }

    fun combine() {
        viewModelScope.launch(Dispatchers.IO) {
            val f1 = flow {
                emit(listOf(1, 2))
            }

            val f2 = flow {
                delay(3000)
                emit(listOf(3, 4))
            }

            val f3 = flow {
                delay(300)
                emit(listOf(5, 6))
            }
            combine(f1, f2, f3) { list, list2, list3 ->
                list + list2 + list3
            }.collect {
                Timber.e("collect : $it")
            }
            // E/==: !![1, 2, 3, 4, 5, 6]
        }
    }

}