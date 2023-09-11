package com.nikit.exchangerates.presentation.sorting

sealed class SortingScreenAction {

    class OnSortAlphabet(val desc: Boolean) : SortingScreenAction()
    class OnSortValue(val desc: Boolean) : SortingScreenAction()
    object OnBackClick : SortingScreenAction()
}