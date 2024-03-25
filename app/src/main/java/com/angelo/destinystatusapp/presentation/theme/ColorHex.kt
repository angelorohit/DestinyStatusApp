package com.angelo.destinystatusapp.presentation.theme

import androidx.compose.ui.graphics.Color

object ColorHex {
    const val PURPLE_80 = 0xFFD0BCFF
    const val PURPLE_GREY_80 = 0xFFCCC2DC
    const val PINK_80 = 0xFFF091A9
    const val PURPLE_40 = 0xFF8871c2
    const val PURPLE_GREY_40 = 0xFF837995
    const val PINK_40 = 0xFF98636f

    fun Long.toColor() = Color(this)
}
