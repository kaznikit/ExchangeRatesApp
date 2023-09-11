package com.nikit.domain.models

import com.nikit.rest.response.RatesResponse

data class RatesModel(
    val base: String,
    val rates: List<RateModel>
)

fun RatesResponse.toDomain() = RatesModel(
    base = base,
    rates = rates.map {
        RateModel(
            it.key,
            it.value
        )
    })