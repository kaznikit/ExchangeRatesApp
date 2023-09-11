package com.nikit.exchangerates.di.modules

import android.content.Context
import com.nikit.exchangerates.App
import com.nikit.exchangerates.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Singleton
    @Provides
    fun provideContext(): Context = App.appContext

    @Singleton
    @Provides
    fun provideNavigator(): Navigator = Navigator()
}