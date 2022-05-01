package com.example.mydemo.main.core.shopping.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.mydemo.getOrAwaitValue
import com.example.mydemo.launchFragmentInHiltContainer
import com.example.mydemo.main.core.shopping.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {
            assertThat(getX()).isEqualTo(100)
        }
    }

    @Test
    fun insertShoppingItem() = runTest {
        val item = ShoppingItem("dog", 20, 2.0f, "url", 1)
        dao.insertShoppingItem(item)
        // the liveData is asynchronous by default, so we need to add rule
        val list = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(list).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val item = ShoppingItem("dog", 20, 2.0f, "url", 1)
        dao.insertShoppingItem(item)
        dao.deleteShoppingItem(item)
        // the liveData is asynchronous by default, so we need to add rule
        val list = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(list).doesNotContain(item)
    }

    @Test
    fun observeTotalPrice() = runTest {
        val item1 = ShoppingItem("dog", 1, 2.0f, "url", 1)
        val item2 = ShoppingItem("dog", 2, 3.0f, "url", 2)
        val item3 = ShoppingItem("dog", 3, 4.0f, "url", 3)
        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)
        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(1 * 2.0f + 2 * 3.0f + 3 * 4.0f)
    }

}