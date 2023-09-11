package com.nikit.domain.repository

import com.nikit.core.resource.Resource
import com.nikit.domain.models.RateModel
import com.nikit.domain.models.SortingMethod
import kotlinx.coroutines.flow.Flow

interface RatesRepository {

    val currencyFlow: Flow<List<RateModel>>

    val favoritesFlow: Flow<List<RateModel>>

    val baseFlow: Flow<RateModel?>

    val alphabetSorting: Flow<SortingMethod>

    val valueSorting: Flow<SortingMethod>

    suspend fun getLatestRates(base: String? = null): Resource<Unit>

    fun insertOrUpdateRate(rate: RateModel)

    fun setBaseCurrency(rate: RateModel)

    fun setAlphabetSorting(sorting: SortingMethod)

    fun setValueSorting(sorting: SortingMethod)
}
