package com.nikit.exchangerates

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        Timber.plant(
            Timber.DebugTree()
        )
    }

    companion object {
        @Volatile
        lateinit var appContext: Context
            private set
    }
}