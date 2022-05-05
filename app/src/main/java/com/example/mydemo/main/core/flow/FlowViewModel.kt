package com.example.mydemo.main.core.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class FlowViewModel {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<Int>()
    val shareFlow = _shareFlow.asSharedFlow()

    suspend fun countDown() = flow {
        var count = 10
        while (count >= 0) {
            delay(300L)
            emit(count)
            count--
        }
    }
}