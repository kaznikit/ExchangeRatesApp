package com.nikit.exchangerates.presentation.sorting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikit.domain.models.SortingMethod
import com.nikit.domain.repository.RatesRepository
import com.nikit.exchangerates.navigation.NavigationDestination
import com.nikit.exchangerates.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SortingScreenViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(SortingScreenState())
    val state: StateFlow<SortingScreenState> = _state

    init {
        viewModelScope.launch {
            ratesRepository.alphabetSorting.collect { value ->
                _state.update { it.copy(byAlphabet = value) }
            }
        }

        viewModelScope.launch {
            ratesRepository.valueSorting.collect { value ->
                _state.update { it.copy(byValue = value) }
            }
        }
    }

    fun onAction(action: SortingScreenAction) {
        when (action) {
            is SortingScreenAction.OnSortAlphabet -> onAlphabetSorting(action.desc)
            is SortingScreenAction.OnSortValue -> onValueSorting(action.desc)
            SortingScreenAction.OnBackClick -> navigator.navigate(NavigationDestination.Back())
        }
    }

    private fun onAlphabetSorting(desc: Boolean) {
        val currentValue = _state.value.byAlphabet
        val sorting = when {
            currentValue == SortingMethod.DEFAULT && desc -> SortingMethod.DESC
            currentValue == SortingMethod.DESC && desc.not() -> SortingMethod.DEFAULT
            currentValue == SortingMethod.NONE -> if (desc) SortingMethod.DESC else SortingMethod.DEFAULT
            else -> SortingMethod.NONE
        }
        ratesRepository.setAlphabetSorting(sorting)
    }

    private fun onValueSorting(desc: Boolean) {
        val currentValue = _state.value.byValue
        val sorting = when {
            currentValue == SortingMethod.DEFAULT && desc -> SortingMethod.DESC
            currentValue == SortingMethod.DESC && desc.not() -> SortingMethod.DEFAULT
            currentValue == SortingMethod.NONE -> if (desc) SortingMethod.DESC else SortingMethod.DEFAULT
            else -> SortingMethod.NONE
        }
        ratesRepository.setValueSorting(sorting)
    }

}