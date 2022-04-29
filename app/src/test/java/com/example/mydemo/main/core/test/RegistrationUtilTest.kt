package com.example.mydemo.main.core.test

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("", "123", "123")
        assertThat(result).isFalse()
    }
    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("eddy1820", "", "123")
        assertThat(result).isFalse()
    }

    @Test
    fun `userName is existed returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("eddy", "123", "123")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty confirmPassword not the same as the real password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput("", "q123", "q123")
        assertThat(result).isFalse()
    }

    @Test
    fun `the password is not contains less than 2 digits`() {
        val result = RegistrationUtil.validateRegistrationInput("eddy1820", "qq3", "qq3")
        assertThat(result).isFalse()
    }

}