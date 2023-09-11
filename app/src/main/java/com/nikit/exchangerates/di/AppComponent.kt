package com.nikit.exchangerates.di

import com.nikit.exchangerates.di.modules.DataModule
import com.nikit.exchangerates.di.modules.DbModule
import com.nikit.exchangerates.di.modules.RestModule
import com.nikit.exchangerates.di.modules.UtilsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        RestModule::class,
        DbModule::class,
        UtilsModule::class
    ]
)
interface AppComponent