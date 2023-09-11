package com.nikit.exchangerates.di.modules

import android.content.Context
import androidx.room.Room
import com.nikit.exchangerates.data.database.RatesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ): RatesDatabase = Room
        .databaseBuilder(
            context,
            RatesDatabase::class.java,
            "rates_db"
        )
        .allowMainThreadQueries()
        .build()
}