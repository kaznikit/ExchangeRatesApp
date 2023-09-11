package com.nikit.exchangerates.presentation.popular

import com.nikit.exchangerates.models.Currency

data class PopularScreenState(
    val selectedCurrency: Currency = Currency(),
    val currencyList: List<Currency> = listOf()
)