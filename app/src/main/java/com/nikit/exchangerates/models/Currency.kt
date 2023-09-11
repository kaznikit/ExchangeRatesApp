package com.nikit.exchangerates.models

import com.nikit.domain.models.RateModel

data class Currency(
    val name: String = "",
    val course: Double = 0.0,
    val isFavorite: Boolean = false,
    val isBase: Boolean = false
)

fun RateModel.toUiModel() = Currency(symbol, course, isFavorite, isBase)