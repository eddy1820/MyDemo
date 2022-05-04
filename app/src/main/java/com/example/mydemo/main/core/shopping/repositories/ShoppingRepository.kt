package com.example.mydemo.main.core.shopping.repositories

import androidx.lifecycle.LiveData
import com.example.mydemo.main.core.shopping.data.local.ShoppingItem
import com.example.mydemo.main.core.shopping.data.remote.responses.ImageResponse
import com.example.mydemo.main.core.shopping.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

    fun getXXX(): Int
}