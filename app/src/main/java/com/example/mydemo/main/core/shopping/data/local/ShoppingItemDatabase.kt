package com.example.mydemo.main.core.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mydemo.main.core.shopping.data.local.ShoppingDao

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}