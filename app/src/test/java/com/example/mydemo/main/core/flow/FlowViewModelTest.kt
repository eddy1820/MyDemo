package com.example.mydemo.main.core.flow

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test

@ExperimentalCoroutinesApi
class FlowViewModelTest {


    lateinit var viewModel: FlowViewModel


    @Before
    fun setUp() {
        viewModel = FlowViewModel()
    }

    @Test
    fun `test count down`() = runTest {
        viewModel.countDown().test {
            for (i in 10 downTo 0) {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

}