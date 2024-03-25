package com.angelo.destinystatusapp.presentation.viewmodel

sealed class UiState<out SUCCESS_TYPE, out ERROR_TYPE> {
    data class Success<SUCCESS_TYPE>(
        val data: SUCCESS_TYPE,
    ) : UiState<SUCCESS_TYPE, Nothing>()

    data class Error<SUCCESS_TYPE, ERROR_TYPE>(
        val existingData: SUCCESS_TYPE,
        val errorData: ERROR_TYPE,
    ) : UiState<SUCCESS_TYPE, ERROR_TYPE>()

    data class Loading<SUCCESS_TYPE>(
        val existingData: SUCCESS_TYPE,
    ) : UiState<SUCCESS_TYPE, Nothing>()

    data object Zero : UiState<Nothing, Nothing>()
}
