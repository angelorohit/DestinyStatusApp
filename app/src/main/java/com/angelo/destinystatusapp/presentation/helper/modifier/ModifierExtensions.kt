package com.angelo.destinystatusapp.presentation.helper.modifier

import androidx.compose.ui.Modifier

inline fun Modifier.conditional(condition: Boolean, applyIfTrue: Modifier.() -> Modifier): Modifier = if (condition) {
    then(applyIfTrue(Modifier))
} else {
    this
}
