package com.nikit.rest.calladapter

import com.nikit.core.network.ApiResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

class ApiCallAdapterFactory private constructor(private val transformer: ApiResponseTransformer) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Flow::class.java -> {
            val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(responseType)) {
                ApiResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, responseType as ParameterizedType)
                    FlowApiCallAdapter<Any>(resultType, transformer)
                }
                else -> null
            }
        }
        Call::class.java -> {
            val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(responseType)) {
                ApiResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, responseType as ParameterizedType)
                    ApiCallAdapter<Any>(resultType, transformer)
                }
                //or handle response with parameterized error
                else -> null
            }
        }
        else -> null
    }

    companion object {
        @JvmStatic
        fun create(transformer: ApiResponseTransformer) = ApiCallAdapterFactory(transformer)
    }

}