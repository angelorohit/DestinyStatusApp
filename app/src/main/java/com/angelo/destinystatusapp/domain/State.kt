package com.angelo.destinystatusapp.domain

sealed class State<out SUCCESS_TYPE> {
    data class Success<SUCCESS_TYPE>(val data: SUCCESS_TYPE) : State<SUCCESS_TYPE>()

    data class Error(val errorType: ErrorType) : State<Nothing>()

    sealed class ErrorType {
        sealed class Remote : ErrorType() {
            data class Request(val httpStatusCode: Int, val message: String = "") : Remote()
            data object NoConnectivity : Remote()
            data object Timeout : Remote()
            data object Unknown : Remote()
        }
    }
}

fun <SUCCESS_TYPE, TRANSFORMED_TYPE> State<SUCCESS_TYPE>.map(
    transform: (SUCCESS_TYPE) -> TRANSFORMED_TYPE,
): State<TRANSFORMED_TYPE> {
    return when (this) {
        is State.Success -> State.Success(transform(data))
        is State.Error -> this
    }
}
