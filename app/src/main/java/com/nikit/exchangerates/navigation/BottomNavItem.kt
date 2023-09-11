package com.nikit.exchangerates.navigation

import com.nikit.exchangerates.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Popular : BottomNavItem("Popular", R.drawable.ic_star_outlined, "popular")
    object Favorite : BottomNavItem("Favorite", R.drawable.ic_heart, "favorite")
}