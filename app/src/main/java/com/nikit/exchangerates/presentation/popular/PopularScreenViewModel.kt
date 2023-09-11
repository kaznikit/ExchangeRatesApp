package com.nikit.exchangerates.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikit.core.resource.onError
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
import timber.log.Timber

@HiltViewModel
class PopularScreenViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(PopularScreenState())
    val state: StateFlow<PopularScreenState> = _state

    init {
        viewModelScope.launch {
            ratesRepository.getLatestRates().onError {
                Timber.e(it.toString())
                //todo в дальнейшем передавать ошибку для отображения на ui
            }
        }
        viewModelScope.launch {
            ratesRepository.currencyFlow.collect { currencies ->
                val currenciesUi = currencies.filter { it.isBase.not() }.map { it.toUiModel() }
                _state.update { screenState ->
                    screenState.copy(
                        currencyList = currenciesUi,
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

    fun onAction(action: PopularScreenAction) {
        when (action) {
            is PopularScreenAction.FavoriteClick -> onFavoriteClick(action.currency)
            is PopularScreenAction.OnCurrencySelect -> currencySelected(action.currency)
            PopularScreenAction.OnSortingClick -> navigator.navigate(NavigationDestination.Sorting())
        }
    }

    private fun currencySelected(currency: Currency) {
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