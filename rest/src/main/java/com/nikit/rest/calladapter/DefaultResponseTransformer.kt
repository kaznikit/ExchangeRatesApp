package com.nikit.rest.calladapter

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.nikit.core.error.Failure
import com.nikit.core.network.ApiResponse
import com.nikit.rest.response.ErrorResponse
import java.net.UnknownHostException
import retrofit2.Response
import timber.log.Timber

class DefaultResponseTransformer(
    private val gson: Gson,
) : ApiResponseTransformer {

    override fun <N> transform(response: Response<N>): ApiResponse<N> {
        return if (response.isSuccessful) {
            transformSuccessful(response)
        } else {
            transformUnsuccessful(response)
        }
    }

    override fun <N> transform(error: Throwable): ApiResponse<N> {
        Timber.e(error.localizedMessage)
        return when (error) {
            is UnknownHostException -> ApiResponse.Error(Failure.NetworkFailure)
            else -> ApiResponse.Error(error.message ?: "Unknown error")
        }
    }

    private fun <N> transformSuccessful(response: Response<N>): ApiResponse<N> {
        val body = response.body()
        return if (body != null) {
            ApiResponse.Success(body)
        } else {
            val cause = "Null body error"
            Timber.e(cause)
            ApiResponse.Error(cause)
        }
    }

    private fun <N> transformUnsuccessful(response: Response<N>): ApiResponse<N> {
        val errorBodyString = response.errorBody()?.string()
        return ApiResponse.Error(parseServerError(errorBodyString))
    }

    private fun parseServerError(errorBody: String?): Failure.ServerError {
        Timber.e(errorBody)
        if (errorBody.isNullOrBlank()) return Failure.ServerError.Unknown
        return try {
            gson.fromJson(errorBody, ErrorResponse.Default::class.java).toFailure()
        } catch (e: JsonParseException) {
            Timber.e(e, "Exception in parseServerError")
            Failure.ServerError.Unknown
        }
    }

}