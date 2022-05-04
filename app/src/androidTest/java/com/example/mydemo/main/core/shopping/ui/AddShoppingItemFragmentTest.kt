package com.example.mydemo.main.core.shopping.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.mydemo.R
import com.example.mydemo.getOrAwaitValue
import com.example.mydemo.launchFragmentInHiltContainer
import com.example.mydemo.main.core.shopping.data.local.ShoppingItem
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        var fragment: AddShoppingItemFragment? = null
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            requireActivity().runOnUiThread {
                Navigation.setViewNavController(requireView(), navController)
            }
            fragment = this
        }

        onView(withId(R.id.etShoppingItemName))
            .perform(ViewActions.replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(ViewActions.replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice))
            .perform(ViewActions.replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(ViewActions.click())
        assertThat(fragment?.viewModel?.shoppingItems?.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            requireActivity().runOnUiThread {
                Navigation.setViewNavController(requireView(), navController)
            }
        }

        pressBack()
        Mockito.verify(navController).popBackStack()
    }
}