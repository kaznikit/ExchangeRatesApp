package com.nikit.exchangerates.presentation.sorting

import com.nikit.domain.models.SortingMethod

data class SortingScreenState(
    val byAlphabet: SortingMethod = SortingMethod.NONE,
    val byValue: SortingMethod = SortingMethod.NONE
)