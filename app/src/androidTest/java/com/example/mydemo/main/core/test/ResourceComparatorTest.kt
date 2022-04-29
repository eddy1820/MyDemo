package com.example.mydemo.main.core.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.mydemo.R
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparatorTest {

    private lateinit var resourceComparator: ResourceComparator

    @Before
    fun setup() {
        resourceComparator = ResourceComparator()
    }

    @After
    fun teardown() {

    }

    @Test
    fun stringResourceSameAsGivenString_returnTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertThat(resourceComparator.isEqual(context, R.string.app_name, "MyDemo")).isTrue()
    }


    @Test
    fun stringResourceNotSameAsGivenString_returnFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertThat(resourceComparator.isEqual(context, R.string.app_name, "MyEddy")).isFalse()
    }

}