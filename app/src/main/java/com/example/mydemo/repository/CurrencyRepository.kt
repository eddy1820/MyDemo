package com.example.mydemo.repository


import com.example.mydemo.db.CurrencyDao
import com.example.mydemo.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyDao: CurrencyDao) {

  suspend fun getAllCurrency(): Flow<List<CurrencyInfo>> {
    return currencyDao.getAllCurrency()
  }
}