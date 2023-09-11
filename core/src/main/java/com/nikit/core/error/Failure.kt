package com.nikit.core.error

sealed interface Failure {

    object Nothing : Failure

    /**
     * Hierarchy of classes indicates failures on server side
     */
    sealed class ServerError(
        open val code: Int,
        open val message: String
    ) : Failure {

        override fun toString(): String =
            "${javaClass.simpleName} [code: $code, message: $message]"

        object Unknown : ServerError(
            0,
            "Unknown"
        )

        class Default(
            override val code: Int,
            override val message: String
        ) : ServerError(
            code,
            message
        )
    }

    object NetworkFailure : Failure

    data class ThrowableFailure(val throwable: Throwable) : Failure

}