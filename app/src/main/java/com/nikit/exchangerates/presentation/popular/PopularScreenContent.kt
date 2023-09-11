package com.nikit.exchangerates.presentation.popular

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikit.exchangerates.R
import com.nikit.exchangerates.presentation.components.CurrencyDropdownMenu
import com.nikit.exchangerates.presentation.components.CurrencyItem

@Composable
fun PopularScreenContent(popularScreenViewModel: PopularScreenViewModel = hiltViewModel()) {
    val state by popularScreenViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CurrencyDropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selectedCurrencyName = state.selectedCurrency.name,
                currencies = state.currencyList,
                onItemClick = {
                    popularScreenViewModel.onAction(PopularScreenAction.OnCurrencySelect(it))
                }
            )
            IconButton(onClick = {
                popularScreenViewModel.onAction(PopularScreenAction.OnSortingClick)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = stringResource(
                        id = R.string.sort_icon_cd
                    )
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = state.currencyList) { item ->
                CurrencyItem(model = item, onFavoriteClick = {
                    popularScreenViewModel.onAction(PopularScreenAction.FavoriteClick(item))
                })
            }
        }
    }
}