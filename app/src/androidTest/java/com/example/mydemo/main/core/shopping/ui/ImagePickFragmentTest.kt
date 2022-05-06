package com.example.mydemo.main.core.shopping.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.mydemo.R
import com.example.mydemo.getOrAwaitValue
import com.example.mydemo.launchFragmentInHiltContainer
import com.example.mydemo.main.core.shopping.repositories.FakeShoppingRepositoryAndroidTest
import com.example.mydemo.main.core.shopping.ui.adapter.ImageAdapter
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ImagePickFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        val imageUrl = "TEST"
        val navController = Mockito.mock(NavController::class.java)
        var fragment: ImagePickFragment? = null

        launchFragmentInHiltContainer<ImagePickFragment>() {
            requireActivity().runOnUiThread {
                Navigation.setViewNavController(requireView(), navController)
            }
            imageAdapter.images = listOf(imageUrl)
            fragment = this
            assertThat(fragment?.viewModel?.getXXX()).isEqualTo(200)
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(fragment?.viewModel?.curImageUrl?.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}