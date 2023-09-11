package com.nikit.core.network

import com.nikit.core.error.Failure
import com.nikit.core.resource.Resource

sealed class ApiResponse<out N> {

    data class Success<out N>(val data: N) : ApiResponse<N>()

    data class Error(val error: Failure) : ApiResponse<Nothing>() {
        constructor(errorMessage: String) :
                this(Failure.ThrowableFailure(Exception(errorMessage)))
    }

    val isSuccess get() = this is Success<N>

    val isError get() = this is Error

    fun error(failure: Throwable) =
        Error(failure)

    fun <R> success(data: R) =
        Success(data)

    inline fun fold(
        crossinline onFailure: (Failure) -> Any,
        crossinline onSuccess: (N) -> Any
    ): Any =
        when (this) {
            is Error -> onFailure(error)
            is Success -> onSuccess(data)
        }

    inline fun <R> toResource(transform: (N) -> R): Resource<R> {
        return when (this) {
            is Success -> Resource.Success(transform(this.data))
            is Error -> Resource.Error(this.error)
        }
    }

}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

inline fun <T, R> ApiResponse<R>.flatMap(fn: (R) -> ApiResponse<T>): ApiResponse<T> =
    when (this) {
        is ApiResponse.Error -> ApiResponse.Error(error)
        is ApiResponse.Success -> fn(data)
    }

fun <T, R> ApiResponse<R>.map(fn: (R) -> (T)): ApiResponse<T> = this.flatMap(fn.c(::success))

inline fun <R> ApiResponse<R>.onSuccess(fn: (R) -> Unit): ApiResponse<R> {
    if (this is ApiResponse.Success) fn(data)
    return this
}

inline fun <R> ApiResponse<R>.onError(fn: (Failure) -> Unit): ApiResponse<R> {
    if (this is ApiResponse.Error) fn(error)
    return this
}

fun ApiResponse.Error.toResourceError() = Resource.Error(this.error)
