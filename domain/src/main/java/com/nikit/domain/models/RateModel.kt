package com.nikit.domain.models


data class RateModel(
    val symbol: String,
    val course: Double,
    val isFavorite: Boolean = false,
    val isBase: Boolean = false
)