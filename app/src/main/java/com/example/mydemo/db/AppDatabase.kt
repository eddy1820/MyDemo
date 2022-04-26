package com.example.mydemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mydemo.model.CurrencyInfo

@Database(entities = [(CurrencyInfo::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    private const val DB_NAME = "currency.db"
    private const val ASSETS_DB = "db/database.db"

    @Volatile
    private var instance: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase =
      instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .createFromAsset(ASSETS_DB)
        .build()
  }

  abstract fun getCurrencyDao(): CurrencyDao
}