package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource

@Stable
sealed class UiString {
    data class DynamicString(val value: String) : UiString()
    class StringResource(@StringRes val id: Int, vararg val formatArgs: Any) : UiString()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(id, formatArgs)
    }
}
