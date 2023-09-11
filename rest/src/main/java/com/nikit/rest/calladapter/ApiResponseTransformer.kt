package com.nikit.rest.calladapter

import com.nikit.core.network.ApiResponse
import retrofit2.Response

interface ApiResponseTransformer {

    fun <N> transform(response: Response<N>): ApiResponse<N>

    fun <N> transform(error: Throwable): ApiResponse<N>
}