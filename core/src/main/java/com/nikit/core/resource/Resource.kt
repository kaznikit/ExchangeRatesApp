package com.nikit.core.resource

import com.nikit.core.error.Failure

sealed class Resource<out R> {

    data class Success<out R>(val data: R) : Resource<R>()

    data class Error(val failure: Failure) : Resource<Nothing>()

    val isSuccess get() = this is Success<R>

    val isError get() = this is Error

    inline fun fold(
        onFailure: (Failure) -> Unit,
        onSuccess: (R) -> Unit
    ) {
        when (this) {
            is Error -> onFailure(failure)
            is Success -> onSuccess(data)
        }
    }

    internal fun error(failure: Failure) =
        Error(failure)

    internal fun <R> success(data: R) =
        Success(data)

}