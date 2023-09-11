package com.nikit.rest.response

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)
