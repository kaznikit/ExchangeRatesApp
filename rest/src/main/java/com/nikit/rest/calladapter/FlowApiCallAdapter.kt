package com.nikit.rest.calladapter

import com.nikit.core.network.ApiResponse
import java.lang.reflect.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse

class FlowApiCallAdapter<N : Any>(
    private val responseType: Type,
    private val responseTransformer: ApiResponseTransformer
) : CallAdapter<N, Flow<ApiResponse<N>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<N>): Flow<ApiResponse<N>> = flow {
        emit(responseTransformer.transform(call.awaitResponse()))
    }.catch { error ->
        emit(responseTransformer.transform(error))
    }
}