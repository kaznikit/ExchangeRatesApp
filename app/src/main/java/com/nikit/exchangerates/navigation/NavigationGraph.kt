package com.nikit.exchangerates.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nikit.exchangerates.presentation.favorite.FavoriteScreenContent
import com.nikit.exchangerates.presentation.popular.PopularScreenContent
import com.nikit.exchangerates.presentation.sorting.SortingScreenContent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationGraph(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect(Unit) {
        navigator.destinationsFlow.onEach {
            if(it.route.isEmpty()) {
                navController.popBackStack()
            }
            else {
                navController.navigate(it.route)
            }
        }.launchIn(this)
    }

    NavHost(
        modifier = Modifier.padding(all = 8.dp),
        navController = navController,
        startDestination = BottomNavItem.Popular.route
    ) {
        composable(BottomNavItem.Popular.route) {
            PopularScreenContent()
        }
        composable(BottomNavItem.Favorite.route) {
            FavoriteScreenContent()
        }
        composable("sorting") {
            SortingScreenContent()
        }
    }
}