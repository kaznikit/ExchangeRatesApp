package com.nikit.exchangerates.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {
    private val _destinationsFlow =
        MutableSharedFlow<NavigationDestination>(extraBufferCapacity = 1)
    val destinationsFlow = _destinationsFlow.asSharedFlow()

    fun navigate(destination: NavigationDestination) {
        _destinationsFlow.tryEmit(destination)
    }
}