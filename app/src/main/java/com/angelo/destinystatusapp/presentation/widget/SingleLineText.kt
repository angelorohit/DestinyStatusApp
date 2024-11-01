package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SingleLineText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
