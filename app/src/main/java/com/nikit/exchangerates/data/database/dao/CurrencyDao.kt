package com.nikit.exchangerates.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nikit.exchangerates.data.database.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currencies: List<CurrencyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyEntity)

    @Query("SELECT * FROM CurrencyEntity")
    fun getCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM CurrencyEntity WHERE is_base = 1")
    fun getBase(): Flow<CurrencyEntity?>

}