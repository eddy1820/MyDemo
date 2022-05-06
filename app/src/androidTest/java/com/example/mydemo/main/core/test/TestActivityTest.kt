package com.example.mydemo.main.core.test

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.mydemo.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest


import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //global declaration
//    @get:Rule
//    var activityRule = ActivityScenarioRule(TestActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_ui_visibility() {
        val testActivityScenario = ActivityScenario.launch(TestActivity::class.java)
        onView(withId(R.id.accountEdit)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordEdit)).check(matches(isDisplayed()))
        onView(withId(R.id.titleLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.confirmPasswordEdit)).check(matches(isDisplayed()))
        onView(withId(R.id.confirmPasswordEdit))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_is_Title_display() {
        val testActivityScenario = ActivityScenario.launch(TestActivity::class.java)
        onView(withId(R.id.titleLabel)).check(matches(withText(R.string.my_title)))
    }

    @Test
    fun login_successfully() {
        val testActivityScenario = ActivityScenario.launch(TestActivity::class.java)
        onView(withId(R.id.accountEdit)).perform(replaceText("gary"))
        onView(withId(R.id.passwordEdit)).perform(replaceText("qq1111"))
        onView(withId(R.id.confirmPasswordEdit)).perform(replaceText("qq1111"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.titleLabel)).check(matches(withText(R.string.detail_page)))

        onView(withId(R.id.buttonTv)).perform(click())
//        pressBack()
        onView(withId(R.id.accountEdit)).check(matches(isDisplayed()))
    }

    @Test
    fun login_successfully2() {
        val testActivityScenario = ActivityScenario.launch(TestActivity::class.java)
        onView(withId(R.id.accountEdit)).perform(typeText("gary"), closeSoftKeyboard())
        onView(withId(R.id.passwordEdit)).perform(typeText("qq1111"), closeSoftKeyboard())
        onView(withId(R.id.confirmPasswordEdit)).perform(typeText("qq1111"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.titleLabel)).check(matches(withText(R.string.detail_page)))

        onView(withId(R.id.buttonTv)).perform(click())
//        pressBack()
        onView(withId(R.id.accountEdit)).check(matches(isDisplayed()))
    }

    @Test
    fun login_failed() {
        val testActivityScenario = ActivityScenario.launch(TestActivity::class.java)
        onView(withId(R.id.accountEdit)).perform(typeText("gary"), closeSoftKeyboard())
        onView(withId(R.id.passwordEdit)).perform(typeText("q"), closeSoftKeyboard())
        onView(withId(R.id.confirmPasswordEdit)).perform(typeText("q"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.accountEdit)).check(matches(isDisplayed()))
//        onView(withText(R.string.wrong_information))
//            .inRoot(isDialog())
//            .check(matches(isDisplayed()))
    }
}