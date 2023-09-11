package com.nikit.exchangerates.presentation.favorite

import com.nikit.exchangerates.models.Currency

data class FavoriteScreenState(
    val selectedCurrency: Currency = Currency(),
    val currencyList: List<Currency> = listOf(),
    val dropdownMenuCurrencyList: List<Currency> = listOf()
)