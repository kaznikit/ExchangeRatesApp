package com.nikit.rest.api

import com.nikit.core.network.ApiResponse
import com.nikit.rest.response.RatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getLatest(@Query("from") name: String?): ApiResponse<RatesResponse>

}