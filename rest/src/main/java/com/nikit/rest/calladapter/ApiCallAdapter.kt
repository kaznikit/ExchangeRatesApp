package com.nikit.rest.calladapter

import com.nikit.core.network.ApiResponse
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class ApiCallAdapter<N : Any>(
    private val responseType: Type,
    private val responseTransformer: ApiResponseTransformer
) : CallAdapter<N, Call<ApiResponse<N>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<N>): Call<ApiResponse<N>> = ApiCall(call, responseTransformer)
}
