package com.example.mydemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyInfo(
  @PrimaryKey
  val id: String,
  @ColumnInfo(name = "name")
  val name: String,
  @ColumnInfo(name = "symbol")
  val symbol: String
) {
  fun getFirstLetterOfSymbol(): String {
    return symbol.getOrNull(0).toString()
  }
}