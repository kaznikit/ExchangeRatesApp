package com.nikit.exchangerates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikit.exchangerates.data.database.dao.CurrencyDao
import com.nikit.exchangerates.data.database.entity.CurrencyEntity

@Database(
    entities = [
        CurrencyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RatesDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}