package com.nikit.rest.response

import com.nikit.core.error.Failure

sealed class ErrorResponse(
    @Transient open val code: Int,
    @Transient open val message: String
) {
    abstract fun toFailure(): Failure.ServerError

    object Unknown : ErrorResponse(
        0,
        "Unknown"
    ) {
        override fun toFailure() = Failure.ServerError.Unknown
    }

    class Default(
        override val code: Int,
        override val message: String
    ) : ErrorResponse(
        code,
        message
    ) {
        override fun toFailure() = Failure.ServerError.Default(
            code,
            message
        )
    }
}