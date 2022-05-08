package com.example.mydemo.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmitTime = 0L
    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmitTime > duration) {
            lastEmitTime = currentTime
            emit(value)
        }
    }
}
