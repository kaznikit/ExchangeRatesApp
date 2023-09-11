package com.nikit.exchangerates.navigation

sealed class NavigationDestination(val route: String) {

    class Sorting(route: String = "sorting") : NavigationDestination(route)
    class Back(route: String = "") : NavigationDestination(route)
}