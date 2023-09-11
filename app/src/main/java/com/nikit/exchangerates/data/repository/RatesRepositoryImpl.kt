package com.nikit.exchangerates.data.repository

import com.nikit.core.network.onSuccess
import com.nikit.core.resource.Resource
import com.nikit.domain.models.RateModel
import com.nikit.domain.models.SortingMethod
import com.nikit.domain.models.toDomain
import com.nikit.domain.repository.RatesRepository
import com.nikit.exchangerates.data.database.RatesDatabase
import com.nikit.exchangerates.data.database.entity.CurrencyEntity
import com.nikit.exchangerates.data.database.entity.toDb
import com.nikit.exchangerates.data.database.entity.toDomain
import com.nikit.rest.api.CurrencyApi
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatesRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val scope: CoroutineScope,
    ratesDatabase: RatesDatabase
) : RatesRepository {

    private val currencyDao = ratesDatabase.currencyDao()

    private val _currencyFlow = MutableStateFlow(listOf<RateModel>())
    override val currencyFlow: Flow<List<RateModel>> = _currencyFlow

    override val favoritesFlow: Flow<List<RateModel>> =
        currencyFlow.map { rateModelList -> rateModelList.filter { it.isFavorite && it.isBase.not() } }

    private val _baseFlow = MutableStateFlow<RateModel?>(null)
    override val baseFlow: Flow<RateModel?> = _baseFlow

    private val _alphabetSorting = MutableStateFlow(SortingMethod.NONE)
    override val alphabetSorting: Flow<SortingMethod> = _alphabetSorting

    private val _valueSorting = MutableStateFlow(SortingMethod.NONE)
    override val valueSorting: Flow<SortingMethod> = _valueSorting

    init {
        scope.launch {
            currencyDao.getCurrencies().collect { currencies ->
                _currencyFlow.update { currencies.toDomain() }
            }
        }

        scope.launch {
            currencyDao.getBase().collect { base ->
                base?.let {
                    _baseFlow.update { base.toDomain() }
                }
            }
        }
    }

    override suspend fun getLatestRates(base: String?): Resource<Unit> =
        currencyApi.getLatest(base)
            .onSuccess { ratesResponse ->
                val oldList = _currencyFlow.value
                currencyDao.insertAllCurrencies(ratesResponse.rates.map { currency ->
                    CurrencyEntity(
                        name = currency.key,
                        course = currency.value,
                        isFavorite = oldList.firstOrNull { it.symbol == currency.key }?.isFavorite
                            ?: false,
                        isBase = false
                    )
                } + CurrencyEntity(
                    name = ratesResponse.base,
                    course = 0.0,
                    isFavorite = oldList.firstOrNull { it.symbol == ratesResponse.base }?.isFavorite
                        ?: false,
                    isBase = true
                ))
            }
            .toResource { it.toDomain() }

    override fun insertOrUpdateRate(rate: RateModel) {
        scope.launch {
            currencyDao.insertCurrency(rate.toDb())
        }
    }

    override fun setBaseCurrency(rate: RateModel) {
        val baseCurrency = _currencyFlow.value.first { it.isBase }
        scope.launch {
            currencyDao.insertCurrency(baseCurrency.copy(isBase = baseCurrency.isBase.not()).toDb())
            currencyDao.insertCurrency(rate.toDb())

            getLatestRates(rate.symbol)
        }
    }

    override fun setAlphabetSorting(sorting: SortingMethod) {
        _alphabetSorting.update { sorting }

        val currencies = _currencyFlow.value
        val currenciesUpdated = when (_alphabetSorting.value) {
            SortingMethod.DEFAULT -> currencies.sortedBy { it.symbol }
            SortingMethod.DESC -> currencies.sortedByDescending { it.symbol }
            else -> currencies
        }

        _currencyFlow.update { currenciesUpdated }
    }

    override fun setValueSorting(sorting: SortingMethod) {
        _valueSorting.update { sorting }

        val currencies = _currencyFlow.value
        val currenciesUpdated = when (_valueSorting.value) {
            SortingMethod.DEFAULT -> currencies.sortedBy { it.course }
            SortingMethod.DESC -> currencies.sortedByDescending { it.course }
            else -> currencies
        }
        _currencyFlow.update { currenciesUpdated }
    }
}