package com.nikit.exchangerates.presentation.popular

import com.nikit.exchangerates.models.Currency

sealed class PopularScreenAction {

    data class FavoriteClick(val currency: Currency) : PopularScreenAction()

    data class OnCurrencySelect(val currency: Currency) : PopularScreenAction()

    object OnSortingClick : PopularScreenAction()
}