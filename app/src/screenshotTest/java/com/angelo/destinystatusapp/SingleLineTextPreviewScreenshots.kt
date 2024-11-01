package com.angelo.destinystatusapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.SingleLineText

@Stable
@Suppress("unused")
class SingleLineTextPreviewScreenshots {

    @Composable
    @PreviewLightDark
    private fun SingleLineTextPreview() {
        DestinyStatusAppTheme {
            Surface {
                SingleLineText(
                    modifier = Modifier.padding(16.dp),
                    text = "Hello world!",
                )
            }
        }
    }
}