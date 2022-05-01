package com.example.mydemo.main.core.shopping.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var price: Float,
    var imgUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
