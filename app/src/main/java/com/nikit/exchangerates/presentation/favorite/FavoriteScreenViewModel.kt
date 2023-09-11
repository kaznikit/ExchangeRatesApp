package com.nikit.exchangerates.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikit.domain.models.RateModel
import com.nikit.domain.repository.RatesRepository
import com.nikit.exchangerates.models.Currency
import com.nikit.exchangerates.models.toUiModel
import com.nikit.exchangerates.navigation.NavigationDestination
import com.nikit.exchangerates.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> = _state

    init {
        viewModelScope.launch {
            ratesRepository.favoritesFlow.collect { currencies ->
                val favorites = currencies.map { it.toUiModel() }
                _state.update { screenState ->
                    screenState.copy(
                        currencyList = favorites,
                        dropdownMenuCurrencyList = favorites,
                    )
                }
            }
        }

        viewModelScope.launch {
            ratesRepository.baseFlow.collect { base ->
                base?.let { baseNotNull ->
                    _state.update {
                        it.copy(selectedCurrency = baseNotNull.toUiModel())
                    }
                }
            }
        }
    }

    fun onAction(action: FavoriteScreenAction) {
        when (action) {
            is FavoriteScreenAction.FavoriteClick -> onFavoriteClick(action.currency)
            is FavoriteScreenAction.OnCurrencySelect -> onCurrencySelected(action.currency)
            FavoriteScreenAction.OnSortingClick -> navigator.navigate(NavigationDestination.Sorting())
        }
    }

    private fun onCurrencySelected(currency: Currency) {
    /*    _state.update { screenState ->
            screenState.copy(currencyList = _state.value.currencyList.filter { it.name == currency.name })
        }*/
        _state.update {
            it.copy(selectedCurrency = currency)
        }
        ratesRepository.setBaseCurrency(
            RateModel(
                currency.name,
                currency.course,
                currency.isFavorite,
                true
            )
        )
    }

    private fun onFavoriteClick(currency: Currency) {
        ratesRepository.insertOrUpdateRate(
            RateModel(
                currency.name,
                currency.course,
                currency.isFavorite.not()
            )
        )
    }
}