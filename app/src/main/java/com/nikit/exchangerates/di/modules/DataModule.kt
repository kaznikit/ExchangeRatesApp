package com.nikit.exchangerates.di.modules

import com.nikit.domain.repository.RatesRepository
import com.nikit.exchangerates.data.repository.RatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindRatesRepository(repository: RatesRepositoryImpl): RatesRepository

}