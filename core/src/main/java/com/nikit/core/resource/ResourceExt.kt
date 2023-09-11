package com.nikit.core.resource

import com.nikit.core.error.Failure
import kotlinx.coroutines.flow.Flow

private fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

inline fun <T, R> Resource<R>.flatMap(fn: (R) -> Resource<T>): Resource<T> =
    when (this) {
        is Resource.Error -> Resource.Error(failure)
        is Resource.Success -> fn(data)
    }

fun <T, R> Resource<R>.map(fn: (R) -> (T)): Resource<T> = this.flatMap(fn.c(::success))

inline fun <R> Resource<R>.onSuccess(fn: (R) -> Unit): Resource<R> {
    if (this is Resource.Success) fn(data)
    return this
}

inline fun <R> Resource<R>.onError(fn: (Failure) -> Unit): Resource<R> {
    if (this is Resource.Error) fn(failure)
    return this
}

suspend inline fun <R> Flow<Resource<R>>.collectAndFold(
    crossinline onFailure: (Failure) -> Unit,
    crossinline onSuccess: (R) -> Unit
): Any {
    return collect { result ->
        result.fold(onFailure, onSuccess)
    }
}

fun <T> T.toResourceSuccess() = Resource.Success(this)

fun Failure.toResourceError() = Resource.Error(this)