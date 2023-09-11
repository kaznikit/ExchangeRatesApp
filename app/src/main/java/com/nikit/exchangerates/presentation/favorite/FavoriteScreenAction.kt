package com.nikit.exchangerates.presentation.favorite

import com.nikit.exchangerates.models.Currency

sealed class FavoriteScreenAction {

    data class FavoriteClick(val currency: Currency) : FavoriteScreenAction()

    data class OnCurrencySelect(val currency: Currency) : FavoriteScreenAction()

    object OnSortingClick : FavoriteScreenAction()
}